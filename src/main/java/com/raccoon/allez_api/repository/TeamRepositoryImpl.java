package com.raccoon.allez_api.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.raccoon.allez_api.dto.QTeamDTO;
import com.raccoon.allez_api.dto.TeamDTO;
import com.raccoon.allez_api.entity.QTeam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TeamRepositoryImpl implements TeamRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public TeamRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<TeamDTO> findAllTeams(Pageable pageable) {
        QTeam team = QTeam.team;

        List<TeamDTO> teams = queryFactory
                .select(new QTeamDTO(
                        team.teamId,
                        team.teamName,
                        team.isExist,
                        team.homeStadium,
                        team.currentLeagueTier
                ))
                .from(team)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.select(team.count()).from(team).fetchOne();

        return new PageImpl<>(teams, pageable, total);
    }

    @Override
    public Page<TeamDTO> findTeamsByName(String teamName, Pageable pageable) {
        QTeam team = QTeam.team;

        List<TeamDTO> teams = queryFactory
                .select(new QTeamDTO(
                        team.teamId,
                        team.teamName,
                        team.isExist,
                        team.homeStadium,
                        team.currentLeagueTier
                ))
                .from(team)
                .where(team.teamName.containsIgnoreCase(teamName))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(team.count())
                .from(team)
                .where(team.teamName.containsIgnoreCase(teamName))
                .fetchOne();

        return new PageImpl<>(teams, pageable, total);
    }

    @Override
    public TeamDTO findTeamById(Long teamId) {
        QTeam team = QTeam.team;

        return queryFactory
                .select(new QTeamDTO(
                        team.teamId,
                        team.teamName,
                        team.isExist,
                        team.homeStadium,
                        team.currentLeagueTier
                ))
                .from(team)
                .where(team.teamId.eq(teamId))
                .fetchOne();
    }
}