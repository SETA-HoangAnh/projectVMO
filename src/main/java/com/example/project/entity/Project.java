package com.example.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long projectId;

    @NotEmpty(message = "missing project name")
    @Column(name = "project_name")
    private String projectName;

    @NotEmpty(message = "missing project code")
    @Column(name = "project_code")
    private String projectCode;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    /**
     * 1: Java
     * 2: C#
     * 3: Python
     * 4: JavaScrip
     */
    @Column(name = "coding_language")
    private String codingLanguage;

    /**
     * 1: Not start
     * 2: Ongoing
     * 3: Canceled
     * 4: Closed
     */
    @Column(name = "project_status")
    private String projectStatus;

    @JsonIgnore
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<ProjectUser> projectUsers;
}
