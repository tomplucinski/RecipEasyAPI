package com.project.recipeasy.repositories;

import com.project.recipeasy.models.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepository {

    Profile save(Profile profile);

    List<Profile> findAll();

    Profile findOne(String id);

    long count();

    long delete(String id);

    long deleteAll();

    Profile update(Profile profile);
}
