package com.example.project.entity;

import com.example.project.service.Impl.ProjectUserServiceImpl;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "project_user")
public class ProjectUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_user_id")
    private Long projectUserId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @PostPersist
    public void notiPersist(){
        ProjectUserServiceImpl.printMessagePresit(this.project.getProjectId(), this.users.getUserId());
    }

}
