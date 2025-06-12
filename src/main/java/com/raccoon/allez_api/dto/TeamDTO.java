package com.raccoon.allez_api.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamDTO {
    private Long teamId;
    private String teamName;
    private Boolean isExist;
    private String homeStadium;
    private String currentLeagueTier;

    @QueryProjection
    public TeamDTO(Long teamId, String teamName, Boolean isExist, String homeStadium, String currentLeagueTier) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.isExist = isExist;
        this.homeStadium = homeStadium;
        this.currentLeagueTier = currentLeagueTier;
    }
}