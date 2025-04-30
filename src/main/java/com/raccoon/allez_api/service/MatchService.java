package com.raccoon.allez_api.service;

import com.raccoon.allez_api.dto.MatchDTO;
import com.raccoon.allez_api.repository.MatchRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MatchService {

    private final MatchRepository matchRepository;

    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public Page<MatchDTO> searchMatches(Long teamId, Integer seasonYear, Pageable pageable) {
        return matchRepository.searchMatches(teamId, seasonYear, pageable);
    }
}
