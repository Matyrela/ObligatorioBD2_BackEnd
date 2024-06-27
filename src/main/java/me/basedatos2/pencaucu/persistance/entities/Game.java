package me.basedatos2.pencaucu.persistance.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "game")
public class Game {
    @Id
    @Column(name = "gameid", nullable = false)
    private Integer id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "\"time\"", nullable = false)
    private LocalTime time;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "team1id", nullable = false)
    private Team team1id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "team2id", nullable = false)
    private Team team2id;

    @Column(name = "team1score", nullable = false)
    private Integer team1score;

    @Column(name = "team2score", nullable = false)
    private Integer team2score;

    @Column(name = "stadium", nullable = false, length = 100)
    private String stadium;

}