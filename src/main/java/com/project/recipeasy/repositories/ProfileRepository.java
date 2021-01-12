package com.project.recipeasy.repositories;

import com.project.recipeasy.models.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepository {

    Profile save(Profile profile);

    List<Profile> saveAll(List<Profile> profiles);

    List<Profile> findAll();

    List<Profile> findAll(List<String> ids);

    Profile findOne(String id);

    long count();

    long delete(String id);

    long delete(List<String> ids);

    long deleteAll();

    Profile update(Profile profile);

    long update(List<Profile> profiles);
}
