package com.raccoon.allez_api.service;

import com.raccoon.allez_api.dto.MatchDTO;
import com.raccoon.allez_api.dto.MatchupSummaryDTO;
import com.raccoon.allez_api.repository.MatchRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Service
public class MatchService {

    private final MatchRepository matchRepository;

    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public Page<MatchDTO> searchMatches(Long teamId, Integer seasonYear, Pageable pageable) {
        return matchRepository.searchMatches(teamId, seasonYear, pageable);
    }

    public Page<MatchDTO> getAllMatches(Long teamId1, Long teamId2, Pageable pageable) {
        return matchRepository.matchup(teamId1, teamId2, pageable);
    }

    public MatchupSummaryDTO getSummary(Long teamId1, Long teamId2) {

        Page<MatchDTO> matchup = matchRepository.matchup(teamId1, teamId2, Pageable.unpaged());

        List<MatchDTO> matches = matchup.getContent();

        if (CollectionUtils.isEmpty(matches)) {
            return new MatchupSummaryDTO(teamId1, "", teamId2, "", 0, 0, 0, 0, 0, 0); // No matches found
        }

        return getSummaryDTO(teamId1, teamId2, matches);

    }

    private static MatchupSummaryDTO getSummaryDTO(Long teamId1, Long teamId2, List<MatchDTO> matches) {
        String team1Name = matches.get(0).getTeamName();
        String team2Name = matches.get(0).getOpponentTeamName();

        // 통계 계산
        int totalMatches = matches.size();
        int team1Wins = 0;
        int team2Wins = 0;
        int draws = 0;
        int team1Goals = 0;
        int team2Goals = 0;

        for (MatchDTO match : matches) {

            int gain = match.getGain();
            int loss = match.getLoss();

            team1Goals += gain;
            team2Goals += loss;

            if (gain > loss) {
                team1Wins++;
            }
            if (gain < loss) {
                team2Wins++;
            }
            if (gain == loss) {
                draws++;
            }

        }

        return new MatchupSummaryDTO(teamId1, team1Name, teamId2, team2Name, totalMatches, team1Wins, team2Wins, draws, team1Goals, team2Goals);
    }
}
