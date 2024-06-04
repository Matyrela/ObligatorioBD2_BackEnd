package me.basedatos2.pencaucu.persistance.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "prediction")
public class Prediction {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "game", nullable = false)
    private Game game;

    @Column(name = "points")
    private Integer points;

    @Column(name = "team1score", nullable = false)
    private Integer team1score;

    @Column(name = "team2score", nullable = false)
    private Integer team2score;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student", nullable = false)
    private Student student;


}