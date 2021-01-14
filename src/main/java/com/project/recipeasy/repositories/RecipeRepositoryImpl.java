package com.project.recipeasy.repositories;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.project.recipeasy.models.Recipe;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

@Repository
public class RecipeRepositoryImpl implements RecipeRepository {

    @Autowired
    private MongoClient client;
    private MongoCollection<Recipe> recipeCollection;

    @PostConstruct
    void init() {
        recipeCollection = client.getDatabase("recipeasy").getCollection("recipes", Recipe.class);
    }

    @Override
    public Recipe save(Recipe recipe) {
        recipe.setId(new ObjectId());
        recipeCollection.insertOne(recipe);
        return recipe;
    }

    @Override
    public List<Recipe> findAllByProfileId(String profileId) {
        return recipeCollection.find(eq("profileId", profileId)).into(new ArrayList<>());
    }
}
