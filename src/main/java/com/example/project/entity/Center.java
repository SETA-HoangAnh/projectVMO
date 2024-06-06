package com.example.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Data
@Table(name = "center")
public class Center {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "center_id")
    private Long centerId;

    @NotEmpty(message = "missing center name")
    @Column(name = "center_name")
    private String centerName;

    @JsonIgnore
    @OneToMany(mappedBy = "center", cascade = CascadeType.ALL)
    private List<Users> users;
}
