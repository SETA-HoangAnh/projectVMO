package com.example.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
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

    @NotEmpty(message = "missing user name")
    @Column(name = "user_name")
    private String userName;

    @NotEmpty(message = "missing full name")
    @Column(name = "full_name")
    private String fullName;

    @Size(max = 120)
    @NotEmpty(message = "missing password")
    @Column(name = "password")
    private String password;

    /**
     * 1: Java
     * 2: C#
     * 3: Python
     * 4: JavaScrip
     */
    @NotEmpty(message = "missing coding language")
    @Column(name = "coding_language")
    private String codingLanguage;

    @NotEmpty(message = "missing email")
    @Email(message = "invalid email format")
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

    @JsonIgnore
    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<ProjectUser> projectUsers;


    public Users() {
    }

    public Users(String username, String email, String codingLanguage, String password) {
        this.userName = username;
        this.email = email;
        this.codingLanguage = codingLanguage;
        this.password = password;
    }
}
