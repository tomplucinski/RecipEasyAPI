package com.project.recipeasy.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Ingredient {

    @JsonSerialize(using = ToStringSerializer.class)
    private String name;
    private int numberOfUnits;
    private String unitOfMeasurement;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return numberOfUnits == that.numberOfUnits &&
                name.equals(that.name) &&
                unitOfMeasurement.equals(that.unitOfMeasurement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, numberOfUnits, unitOfMeasurement);
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "name='" + name + '\'' +
                ", numberOfUnits=" + numberOfUnits +
                ", unitOfMeasurement='" + unitOfMeasurement + '\'' +
                '}';
    }
}
