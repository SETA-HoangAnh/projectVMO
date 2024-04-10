package com.example.project.dto;

import javax.persistence.Column;
import javax.validation.constraints.Size;

public interface UserInforDto {

    public Long getUserId();

    public String getUserName();

    public String getFullName();

    public String getCodingLanguage();

    public String getEmail();

    public String getCenterName();
}
