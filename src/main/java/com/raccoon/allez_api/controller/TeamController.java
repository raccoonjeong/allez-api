package com.raccoon.allez_api.controller;

import com.raccoon.allez_api.dto.TeamDTO;
import com.raccoon.allez_api.service.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teams")
@Tag(name = "Team Response", description = "Team Response API")
public class TeamController {
    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    @Operation(summary = "팀 목록 조회", description = "모든 팀 목록 조회")
    public Page<TeamDTO> getTeams(Pageable pageable) {
        return teamService.getTeams(pageable);
    }

    @GetMapping("/search")
    @Operation(summary = "팀 검색", description = "팀 이름으로 검색")
    @Parameters({
            @Parameter(name = "teamName", description = "팀 이름", example = "Arsenal")
    })
    public Page<TeamDTO> searchTeams(
            @RequestParam(name = "teamName") String teamName,
            Pageable pageable) {
        return teamService.searchTeams(teamName, pageable);
    }

    @GetMapping("/details")
    @Operation(summary = "팀 상세 조회", description = "팀 ID로 팀 상세 정보 조회")
    @Parameters({
            @Parameter(name = "teamId", description = "팀 ID", example = "1")
    })
    public TeamDTO getTeamDetails(@RequestParam(name = "teamId") Long teamId) {
        return teamService.getTeamDetails(teamId);
    }
}