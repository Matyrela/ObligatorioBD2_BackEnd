package me.basedatos2.pencaucu.persistance.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "ci", precision = 8)
    private Integer ci;

    @Column(name = "password", length = 150)
    private String password;

    @Column(name = "name", length = 25)
    private String name;

    @Column(name = "lastname", length = 25)
    private String lastname;

    @Column(name = "email", length = 150)
    private String email;

    @Column(name = "birthdate")
    private LocalDate birthdate;

    @Column(name = "score")
    private Short score;

}