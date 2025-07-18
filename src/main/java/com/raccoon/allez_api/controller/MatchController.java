package com.raccoon.allez_api.controller;

import com.raccoon.allez_api.dto.MatchDTO;
import com.raccoon.allez_api.dto.MatchResultDTO;
import com.raccoon.allez_api.dto.MatchupSummaryDTO;
import com.raccoon.allez_api.service.MatchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            @RequestParam(required = false, name = "teamId") Long teamId,
            @RequestParam(required = false, name = "seasonYear") Integer seasonYear,
            Pageable pageable) {

        return matchService.searchMatches(teamId, seasonYear, pageable);
    }

    @GetMapping("/matchup")
    @Operation(summary = "팀 간 매치업 조회", description = "두 팀 간의 매치업 결과 조회")
    @Parameters({
            @Parameter(name = "teamId1", description = "팀 1 ID", example = "1"),
            @Parameter(name = "teamId2", description = "팀 2 ID", example = "2"),
    })
    public Page<MatchDTO> matchup(
            @RequestParam(name = "teamId1") Long teamId1,
            @RequestParam(name = "teamId2") Long teamId2,
            Pageable pageable) {

        return matchService.getAllMatches(teamId1, teamId2, pageable);
    }


    @GetMapping("/summary")
    @Operation(summary = "전적 요약", description = "두 팀 간의 총 전적, 득점, 승무패 수를 반환합니다.")
    public MatchupSummaryDTO getSummary(
            @Parameter(description = "기준 팀 ID") @RequestParam Long teamId1,
            @Parameter(description = "상대 팀 ID") @RequestParam Long teamId2) {
        return matchService.getSummary(teamId1, teamId2);
    }
/*

    @GetMapping("/recent")
    @Operation(summary = "최근 경기 결과", description = "최근 N경기의 승패 결과를 반환합니다.")
    public List<MatchResultDTO> getRecentMatches(
            @RequestParam Long teamId1,
            @RequestParam Long teamId2,
            @RequestParam(defaultValue = "10") int limit) {
        return matchService.getRecentMatches(teamId1, teamId2, limit);
    }

*/
    @GetMapping("/results")
    @Operation(summary = "전체 경기 결과", description = "전체 경기 결과 목록을 반환합니다.")
    public Page<MatchDTO> getAllResults(
            @RequestParam Long teamId1,
            @RequestParam Long teamId2) {
        return matchService.getAllMatches(teamId1, teamId2, null);
    }


}
