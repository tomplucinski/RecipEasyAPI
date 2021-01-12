package com.mongodb.starter.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.bson.types.ObjectId;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class Profile {

    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private String firstName;
    private String lastName;
    private Date createdAt = new Date();
    private List<Recipe> recipes;

    public ObjectId getId() {
        return id;
    }

    public Profile setId(ObjectId id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public Profile setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Profile setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Profile setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public Profile setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
        return this;
    }

    @Override
    public String toString() {
        return "Person{" + "id=" + id + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", createdAt=" + createdAt + ", recipes=" + recipes + '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, createdAt, recipes);
    }
}
