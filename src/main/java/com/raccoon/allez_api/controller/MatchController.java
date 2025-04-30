package com.raccoon.allez_api.controller;

import com.raccoon.allez_api.dto.MatchDTO;
import com.raccoon.allez_api.service.MatchService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/matches")
public class MatchController {
    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping("/search")
    public Page<MatchDTO> searchMatches(
            @RequestParam(required = false) Long teamId,
            @RequestParam(required = false) Integer seasonYear,
            Pageable pageable) {

        return matchService.searchMatches(teamId, seasonYear, pageable);
    }

}
