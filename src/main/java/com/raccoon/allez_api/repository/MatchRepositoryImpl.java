package com.raccoon.allez_api.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.raccoon.allez_api.dto.MatchDTO;
import com.raccoon.allez_api.dto.QMatchDTO;
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
                        t1.teamName,
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
}
