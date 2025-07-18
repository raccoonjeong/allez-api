package com.raccoon.allez_api.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.raccoon.allez_api.dto.MatchDTO;
import com.raccoon.allez_api.dto.MatchupSummaryDTO;
import com.raccoon.allez_api.dto.QMatchDTO;
import com.raccoon.allez_api.dto.QMatchupSummaryDTO;
import com.raccoon.allez_api.entity.QMatch;
import com.raccoon.allez_api.entity.QSeason;
import com.raccoon.allez_api.entity.QTeam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MatchRepositoryImpl implements MatchRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public MatchRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<MatchDTO> searchMatches(Long teamId, Integer seasonYear, Pageable pageable) {

        QMatch m = QMatch.match;
        QTeam t1 = QTeam.team;
        QTeam t2 = new QTeam("opponentTeam");
        QSeason s = QSeason.season;

        List<MatchDTO> content = queryFactory
                .select(new QMatchDTO(
                        m.matchId,
                        t1.teamId,
                        t1.teamName,
                        t2.teamId,
                        t2.teamName,
                        s.seasonYear,
                        m.matchDate,
                        m.homeAway,
                        m.winDrawLoss,
                        m.gain,
                        m.loss
                ))
                .from(m)
                .join(m.team, t1)
                .join(m.opponentTeam, t2)
                .join(m.season, s)
                .where(
                        teamId != null ? m.team.teamId.eq(teamId) : null,
                        seasonYear != null ? s.seasonYear.eq(seasonYear) : null
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(m.count())
                .from(m)
                .join(m.team, t1)
                .join(m.opponentTeam, t2)
                .join(m.season, s)
                .where(teamId != null ? m.team.teamId.eq(teamId) : null,
                        seasonYear != null ? s.seasonYear.eq(seasonYear) : null)
                .fetchOne();

        return new PageImpl<>(content, pageable, total != null ? total : 0);
    }

    @Override
    public Page<MatchDTO> matchup(Long teamId1, Long teamId2, Pageable pageable) {
        QMatch m = QMatch.match;
        QTeam t1 = QTeam.team;
        QTeam t2 = new QTeam("opponentTeam");

        List<MatchDTO> content = queryFactory
                .select(new QMatchDTO(
                        m.matchId,
                        m.team.teamId,
                        m.team.teamName,
                        m.opponentTeam.teamId,
                        m.opponentTeam.teamName,
                        Expressions.asNumber(m.season.seasonYear),
                        m.matchDate,
                        m.homeAway,
                        m.winDrawLoss,
                        Expressions.asNumber(m.gain),
                        Expressions.asNumber(m.loss)
                ))
                .from(m)
                .join(m.team, t1)
                .join(m.opponentTeam, t2)
                .where(
                        m.team.teamId.eq(teamId1).and(m.opponentTeam.teamId.eq(teamId2))
//                                .or(m.team.teamId.eq(teamId2).and(m.opponentTeam.teamId.eq(teamId1)))
                )
                .orderBy(m.matchId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(m.count())
                .from(m)
                .join(m.team, t1)
                .join(m.opponentTeam, t2)
                .where(
                        m.team.teamId.eq(teamId1).and(m.opponentTeam.teamId.eq(teamId2))
                )
                .fetchOne();

        return new PageImpl<>(content, pageable, total != null ? total : 0);
    }

    // 예시 메서드 (MatchRepositoryImpl 내부)
    public MatchupSummaryDTO getMatchupSummary(String team1, String team2) {
        QMatch m = QMatch.match;
        QTeam t1 = QTeam.team;
        QTeam t2 = new QTeam("opponentTeam");

        return queryFactory
                .select(new QMatchupSummaryDTO(
                        m.team.teamId,
                        m.team.teamName,
                        m.opponentTeam.teamId,
                        m.opponentTeam.teamName,
                        Expressions.asNumber(m.count()).intValue(),
                        Expressions.numberTemplate(Integer.class, "SUM(CASE WHEN {0} = 'W' THEN 1 ELSE 0 END)", m.winDrawLoss),
                        Expressions.numberTemplate(Integer.class, "SUM(CASE WHEN {0} = 'D' THEN 1 ELSE 0 END)", m.winDrawLoss),
                        Expressions.numberTemplate(Integer.class, "SUM(CASE WHEN {0} = 'L' THEN 1 ELSE 0 END)", m.winDrawLoss),
                        m.gain.sum(),
                        m.loss.sum()
                ))
                .from(m)
                .join(m.team, t1)
                .join(m.opponentTeam, t2)
                .where(
                        t1.teamName.eq(team1),
                        t2.teamName.eq(team2)
                )
                .fetchOne();
    }
}
