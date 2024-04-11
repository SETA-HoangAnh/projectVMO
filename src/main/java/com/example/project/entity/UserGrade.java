package com.example.project.entity;

//import com.example.project.service.UserGradeServiceImpl;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "user_grade")
public class UserGrade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_grade_id")
    private Long userGradeId;

    @Column(name = "exercise1")
    private Double exercise1;

    @Column(name = "exercise2")
    private Double exercise2;

    @Column(name = "exercise3")
    private Double exercise3;

    @Transient
    private Long userId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private Users users;
}
