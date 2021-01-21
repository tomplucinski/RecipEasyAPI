package com.project.recipeasy.repositories;

import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.TransactionOptions;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.project.recipeasy.models.UserDTO;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.annotation.PostConstruct;

import static com.mongodb.client.model.Filters.eq;


@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final TransactionOptions txnOptions = TransactionOptions.builder()
            .readPreference(ReadPreference.primary())
            .readConcern(ReadConcern.MAJORITY)
            .writeConcern(WriteConcern.MAJORITY)
            .build();

    @Autowired
    private MongoClient client;
    private MongoCollection<UserDTO> userCollection;

    @PostConstruct
    void init() {
        userCollection = client.getDatabase("recipeasy").getCollection("users", UserDTO.class);
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        userDTO.setId(new ObjectId());
        userCollection.insertOne(userDTO);
        return userDTO;
    }

    @Override
    public UserDTO findByUsername(String username) {
        return userCollection.find(eq("username")).first();
    }
}
