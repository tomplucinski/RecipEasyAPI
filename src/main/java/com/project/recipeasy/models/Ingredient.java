package com.project.recipeasy.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.bson.types.ObjectId;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Ingredient {

    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private String recipeId;
    private String name;
    private int numberOfUnits;
    private String unitOfMeasurement;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfUnits() {
        return numberOfUnits;
    }

    public void setNumberOfUnits(int numberOfUnits) {
        this.numberOfUnits = numberOfUnits;
    }

    public String getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public void setUnitOfMeasurement(String unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return numberOfUnits == that.numberOfUnits &&
                Objects.equals(id, that.id) &&
                Objects.equals(recipeId, that.recipeId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(unitOfMeasurement, that.unitOfMeasurement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, recipeId, name, numberOfUnits, unitOfMeasurement);
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", recipeId='" + recipeId + '\'' +
                ", name='" + name + '\'' +
                ", numberOfUnits=" + numberOfUnits +
                ", unitOfMeasurement='" + unitOfMeasurement + '\'' +
                '}';
    }
}
