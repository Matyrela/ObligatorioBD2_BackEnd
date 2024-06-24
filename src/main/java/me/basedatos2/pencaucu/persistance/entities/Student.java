package me.basedatos2.pencaucu.persistance.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "student")
public class Student {
    @Id
    @Column(name = "ci", nullable = false, precision = 8)
    private Long id;

    @Column(name = "password", nullable = false, length = 150)
    private String password;

    @Column(name = "name", nullable = false, length = 25)
    private String name;

    @Column(name = "lastname", nullable = false, length = 25)
    private String lastname;

    @Column(name = "email", nullable = false, length = 150)
    private String email;

    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;

    @Column(name = "score", nullable = false, precision = 3)
    private Long score;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "champion", nullable = false)
    private Team champion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "secondplace", nullable = false)
    private Team secondplace;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "career", nullable = false)
    private Career career;

}