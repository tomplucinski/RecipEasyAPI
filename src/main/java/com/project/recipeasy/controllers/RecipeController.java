package com.project.recipeasy.controllers;

import com.project.recipeasy.models.Recipe;
import com.project.recipeasy.repositories.RecipeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RecipeController {

    private final static Logger LOGGER = LoggerFactory.getLogger(ProfileController.class);
    private final RecipeRepository recipeRepository;

    public RecipeController(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @PostMapping("/recipe")
    @ResponseStatus(HttpStatus.CREATED)
    public Recipe addRecipe(@RequestBody Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    @GetMapping("/recipe/{profileId}")
    public ResponseEntity<List<Recipe>> getAllRecipesForProfile(@PathVariable String profileId) {
        return ResponseEntity.ok(recipeRepository.findAllByProfileId(profileId));
    }
}
