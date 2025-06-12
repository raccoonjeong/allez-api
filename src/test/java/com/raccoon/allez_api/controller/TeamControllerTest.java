package com.raccoon.allez_api.controller;

import com.raccoon.allez_api.dto.TeamDTO;
import com.raccoon.allez_api.service.TeamService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TeamControllerTest {

    @Mock
    private TeamService teamService;

    private TeamController teamController;

    @Test
    void testGetTeams() {
        MockitoAnnotations.openMocks(this);
        teamController = new TeamController(teamService);

        PageRequest pageable = PageRequest.of(0, 10);
        List<TeamDTO> mockTeams = List.of(
                new TeamDTO(1L, "Team A", true, "Stadium A", "Tier 1"),
                new TeamDTO(2L, "Team B", true, "Stadium B", "Tier 2")
        );
        Page<TeamDTO> mockPage = new PageImpl<>(mockTeams, pageable, 2);

        when(teamService.getTeams(pageable)).thenReturn(mockPage);

        Page<TeamDTO> result = teamController.getTeams(pageable);

        assertEquals(2, result.getTotalElements());
        assertEquals("Team A", result.getContent().get(0).getTeamName());
    }

    @Test
    void testSearchTeams() {
        MockitoAnnotations.openMocks(this);
        teamController = new TeamController(teamService);

        PageRequest pageable = PageRequest.of(0, 10);
        List<TeamDTO> mockTeams = List.of(
                new TeamDTO(1L, "Team A", true, "Stadium A", "Tier 1")
        );
        Page<TeamDTO> mockPage = new PageImpl<>(mockTeams, pageable, 1);

        when(teamService.searchTeams("Team A", pageable)).thenReturn(mockPage);

        Page<TeamDTO> result = teamController.searchTeams("Team A", pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals("Team A", result.getContent().get(0).getTeamName());
    }

    @Test
    void testGetTeamDetails() {
        MockitoAnnotations.openMocks(this);
        teamController = new TeamController(teamService);

        TeamDTO mockTeam = new TeamDTO(1L, "Team A", true, "Stadium A", "Tier 1");

        when(teamService.getTeamDetails(1L)).thenReturn(mockTeam);

        TeamDTO result = teamController.getTeamDetails(1L);

        assertEquals(1L, result.getTeamId());
        assertEquals("Team A", result.getTeamName());
    }
}