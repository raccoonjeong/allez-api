package com.raccoon.allez_api.controller;

import com.raccoon.allez_api.dto.MatchDTO;
import com.raccoon.allez_api.service.MatchService;
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
@RequestMapping("/matches")
@Tag(name = "Match Response", description = "Match Response API")
public class MatchController {
    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping("/search")
    @Operation(summary = "경기 조회", description = "팀 ID와 시즌 연도로 경기 결과 조회")
    @Parameters({
            @Parameter(name = "teamId", description = "팀 ID", example = "1"),
            @Parameter(name = "seasonYear", description = "시즌 연도", example = "2024"),
    })
    public Page<MatchDTO> searchMatches(
            @RequestParam(required = false, name="teamId") Long teamId,
            @RequestParam(required = false, name="seasonYear") Integer seasonYear,
            Pageable pageable) {

        return matchService.searchMatches(teamId, seasonYear, pageable);
    }

}
