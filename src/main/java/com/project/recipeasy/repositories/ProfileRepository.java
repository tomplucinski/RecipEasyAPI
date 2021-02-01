package com.project.recipeasy.repositories;

import com.project.recipeasy.models.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository {

    Profile save(Profile profile);

    Optional<Profile> findByEmail(String email);

    List<Profile> findAll();

    Profile findOne(String id);

    long count();

    long delete(String id);

    long deleteAll();

    Profile update(Profile profile);

    UserDetails loadUserByUsername(String email);
}
