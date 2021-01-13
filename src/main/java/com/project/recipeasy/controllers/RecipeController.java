package com.project.recipeasy.controllers;

import com.project.recipeasy.models.Profile;
import com.project.recipeasy.models.Recipe;
import com.project.recipeasy.repositories.ProfileRepository;
import com.project.recipeasy.repositories.RecipeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RecipeController {

    private final static Logger LOGGER = LoggerFactory.getLogger(ProfileController.class);
    private final ProfileRepository profileRepository;
    private final RecipeRepository recipeRepository;

    public RecipeController(RecipeRepository recipeRepository, ProfileRepository profileRepository) {
        this.recipeRepository = recipeRepository;
        this.profileRepository = profileRepository;
    }

    @PostMapping("/profile/{id}/recipe")
    @ResponseStatus(HttpStatus.CREATED)
    public Recipe addRecipe(@PathVariable String id, @RequestBody Recipe recipe) {
        return new Recipe();
    }
}
