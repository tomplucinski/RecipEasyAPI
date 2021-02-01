package com.project.recipeasy.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Profile {

    private ObjectId id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Date createdAt = new Date();
}
