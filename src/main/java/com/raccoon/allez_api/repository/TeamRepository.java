package com.raccoon.allez_api.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.raccoon.allez_api.dto.TeamDTO;
import com.raccoon.allez_api.entity.QTeam;
import com.raccoon.allez_api.entity.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long>, TeamRepositoryCustom {
}