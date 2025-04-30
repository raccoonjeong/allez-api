package com.raccoon.allez_api.repository;

import com.raccoon.allez_api.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
public interface MatchRepository extends JpaRepository<Match, Long>, MatchRepositoryCustom {

    List<Match> findMatchesByTeamIdAndSeasonId(Long teamId, Long seasonId);
}
