package com.mongodb.starter.repositories;

import com.mongodb.starter.models.Profile;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository {

    Profile save(Profile profile);

}
