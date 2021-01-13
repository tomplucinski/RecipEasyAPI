package com.project.recipeasy.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.bson.types.ObjectId;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Recipe {

    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private String profileId;
    private String name;
    private String directions;
    private List<Ingredient> ingredients;
    private Date createdAt = new Date();

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return Objects.equals(id, recipe.id) &&
                Objects.equals(profileId, recipe.profileId) &&
                Objects.equals(name, recipe.name) &&
                Objects.equals(directions, recipe.directions) &&
                Objects.equals(ingredients, recipe.ingredients) &&
                Objects.equals(createdAt, recipe.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, profileId, name, directions, ingredients, createdAt);
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", profileId='" + profileId + '\'' +
                ", name='" + name + '\'' +
                ", directions='" + directions + '\'' +
                ", ingredients=" + ingredients +
                ", createdAt=" + createdAt +
                '}';
    }
}
