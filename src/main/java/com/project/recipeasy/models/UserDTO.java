package com.project.recipeasy.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.bson.types.ObjectId;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private String username;
    private String password;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(id, userDTO.id) &&
                Objects.equals(username, userDTO.username) &&
                Objects.equals(password, userDTO.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password);
    }
}
