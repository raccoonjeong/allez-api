package com.raccoon.allez_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "MATCHES")
public class Match {

    @Id
    @Column(name="MATCH_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "opponent_team_id")
    private Team opponentTeam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "season_id")
    private Season season;
    @Column(name="SEASON_ID")
    private Long seasonId;
    @Column(name="MATCH_DATE")
    private LocalDate matchDate;
    @Column(name="HOME_AWAY")
    private String homeAway;
    @Column(name="WIN_DRAW_LOSS")
    private String winDrawLoss;
    @Column(name="GAIN")
    private Integer gain;
    @Column(name="LOSS")
    private Integer loss;

}
