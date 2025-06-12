package com.raccoon.allez_api.controller;

import com.raccoon.allez_api.dto.TeamDTO;
import com.raccoon.allez_api.service.TeamService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TeamController.class)
class TeamControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeamService teamService;

    @Test
    void testGetTeams() throws Exception {
        PageRequest pageable = PageRequest.of(0, 10);
        List<TeamDTO> mockTeams = List.of(
                new TeamDTO(1L, "Team A", true, "Stadium A", "Tier 1"),
                new TeamDTO(2L, "Team B", true, "Stadium B", "Tier 2")
        );
        Page<TeamDTO> mockPage = new PageImpl<>(mockTeams, pageable, 2);

        when(teamService.getTeams(pageable)).thenReturn(mockPage);

        mockMvc.perform(get("/teams")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].teamName").value("Team A"))
                .andExpect(jsonPath("$.content[1].teamName").value("Team B"));
    }

    @Test
    void testSearchTeams() throws Exception {
        PageRequest pageable = PageRequest.of(0, 10);
        List<TeamDTO> mockTeams = List.of(
                new TeamDTO(1L, "Team A", true, "Stadium A", "Tier 1")
        );
        Page<TeamDTO> mockPage = new PageImpl<>(mockTeams, pageable, 1);

        when(teamService.searchTeams("Team A", pageable)).thenReturn(mockPage);

        mockMvc.perform(get("/teams/search")
                        .param("teamName", "Team A")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].teamName").value("Team A"));
    }

    @Test
    void testGetTeamDetails() throws Exception {
        TeamDTO mockTeam = new TeamDTO(1L, "Team A", true, "Stadium A", "Tier 1");

        when(teamService.getTeamDetails(1L)).thenReturn(mockTeam);

        mockMvc.perform(get("/teams/details")
                        .param("teamId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.teamName").value("Team A"))
                .andExpect(jsonPath("$.teamId").value(1));
    }
}