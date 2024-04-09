package com.example.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;


@Entity
@Data
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_name")
    private String userName;

    @Size(max = 120)
    @Column(name = "password")
    private String password;

    @Column(name = "coding_language")
    private String codingLanguage;

//    //1: manager
//    //2: fresher
//    @Column(name = "position")
//    private String position;

    @Column(name = "email", unique = true)
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserRole> userRoles;

    @ManyToOne
    @JoinColumn(name = "center_id")
    private Center center;

    @OneToOne(mappedBy = "users", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UserGrade userGrade;

    public Users() {
    }

    public Users(String username, String email, String codingLanguage, String password) {
        this.userName = username;
        this.email = email;
        this.codingLanguage = codingLanguage;
        this.password = password;
    }
}
