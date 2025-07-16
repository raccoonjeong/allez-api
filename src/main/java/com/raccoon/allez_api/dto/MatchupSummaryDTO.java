package com.raccoon.allez_api.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class MatchupSummaryDTO {

    private Long teamId;
    private String teamName;
    private Long opponentTeamId;
    private String opponentTeamName;
    private Integer matchCount;
    private Integer winCount;
    private Integer drawCount;
    private Integer lossCount;
    private Integer goalCount;
    private Integer goalAgainstCount;

    @QueryProjection
    public MatchupSummaryDTO(Long teamId, String teamName, Long opponentTeamId, String opponentTeamName, Integer matchCount, Integer winCount, Integer drawCount, Integer lossCount, Integer goalCount, Integer goalAgainstCount) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.opponentTeamId = opponentTeamId;
        this.opponentTeamName = opponentTeamName;
        this.matchCount = matchCount;
        this.winCount = winCount;
        this.drawCount = drawCount;
        this.lossCount = lossCount;
        this.goalCount = goalCount;
        this.goalAgainstCount = goalAgainstCount;
    }

}
