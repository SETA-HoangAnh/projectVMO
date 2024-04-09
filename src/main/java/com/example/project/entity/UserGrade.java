package com.example.project.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "user_grade")
public class UserGrade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userGradeId;

    @Column(name = "exercise1")
    private Long exercise1;

    @Column(name = "exercise2")
    private Long exercise2;

    @Column(name = "exercise3")
    private Long exercise3;

    @Column(name = "averageScore")
    private Long averageScore;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private Users users;
}
