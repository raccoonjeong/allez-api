package com.raccoon.allez_api.service;

import com.raccoon.allez_api.dto.TeamDTO;
import com.raccoon.allez_api.repository.TeamRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Page<TeamDTO> getTeams(Pageable pageable) {
        return teamRepository.findAllTeams(pageable);
    }

    public Page<TeamDTO> searchTeams(String teamName, Pageable pageable) {
        return teamRepository.findTeamsByName(teamName, pageable);
    }

    public TeamDTO getTeamDetails(Long teamId) {
        return teamRepository.findTeamById(teamId);
    }
}