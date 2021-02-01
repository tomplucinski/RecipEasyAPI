package com.project.recipeasy.web.models;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class AuthResponse {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private Date createdAt;
    private String token;

}
