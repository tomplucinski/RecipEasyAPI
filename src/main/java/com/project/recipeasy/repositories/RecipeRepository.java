package com.project.recipeasy.repositories;

import com.project.recipeasy.models.Recipe;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository {

    public Recipe save(Recipe recipe);

    public List<Recipe> findAllByProfileId(String profileId);
}
