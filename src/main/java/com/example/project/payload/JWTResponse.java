package com.example.project.payload;

import lombok.Data;

@Data
public class JWTResponse {

    private String token;
    private String type = "Bearer";
    private Long id;
    private String userName;
    private String email;

    public JWTResponse(String accessToken, Long id, String userName, String email) {
        this.token = accessToken;
        this.id = id;
        this.userName = userName;
        this.email = email;
    }

}
