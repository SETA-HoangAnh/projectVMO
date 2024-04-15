package com.example.project.dto;

import javax.persistence.Column;
import java.time.LocalDate;
import java.util.Date;

public interface ProjectDto {

    public Long getProjectId();

    public String getProjectName();

    public String getProjectCode();

    public LocalDate getStartDate();

    public LocalDate getEndDate();

    public String getCodingLanguage();

    public String getProjectStatus();
}
