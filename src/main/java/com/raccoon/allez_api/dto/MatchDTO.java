package com.raccoon.allez_api.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MatchDTO {
    private Long matchId;
    private String teamName;
    private String opponentTeamName;
    private Integer seasonYear;
    private LocalDate matchDate;
    private String homeAway;
    private String winDrawLoss;
    private Integer gain;
    private Integer loss;

    @QueryProjection
    public MatchDTO(Long matchId, String teamName, String opponentTeamName,
                    Integer seasonYear, LocalDate matchDate, String homeAway,
                    String winDrawLoss, Integer gain, Integer loss) {
        this.matchId = matchId;
        this.teamName = teamName;
        this.opponentTeamName = opponentTeamName;
        this.seasonYear = seasonYear;
        this.matchDate = matchDate;
        this.homeAway = homeAway;
        this.winDrawLoss = winDrawLoss;
        this.gain = gain;
        this.loss = loss;
    }

    // getters
}