package com.example.project.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "role")
public class Role implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name")
    private ERole roleName;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private List<UserRole> userRoles;
}
