package com.raccoon.allez_api.repository;

import com.raccoon.allez_api.dto.TeamDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TeamRepositoryCustom {
    Page<TeamDTO> findAllTeams(Pageable pageable);
    Page<TeamDTO> findTeamsByName(String teamName, Pageable pageable);
    TeamDTO findTeamById(Long teamId);
}