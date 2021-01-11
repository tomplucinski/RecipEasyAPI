package com.mongodb.starter.repositories;

import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.TransactionOptions;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.starter.models.Person;
import com.mongodb.starter.models.Profile;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

@Repository
public class ProfileRepositoryImpl implements ProfileRepository {

    private static final TransactionOptions txnOptions = TransactionOptions.builder()
                                                                        .readPreference(ReadPreference.primary())
                                                                        .readConcern(ReadConcern.MAJORITY)
                                                                        .writeConcern(WriteConcern.MAJORITY)
                                                                        .build();

    @Autowired
    private MongoClient client;
    private MongoCollection<Profile> profileCollection;

    @PostConstruct
    void init() {
        profileCollection = client.getDatabase("recipeasy").getCollection("profiles", Profile.class);
    }

    @Override
    public Profile save(Profile profile) {
        profileCollection.insertOne(profile);
        return profile;
    }
}
