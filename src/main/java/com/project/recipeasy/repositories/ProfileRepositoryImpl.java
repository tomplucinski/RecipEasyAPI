package com.project.recipeasy.repositories;

import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.TransactionOptions;
import com.mongodb.WriteConcern;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.project.recipeasy.models.Profile;
import org.bson.BsonDocument;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.ReturnDocument.AFTER;

@Repository
public class ProfileRepositoryImpl implements ProfileRepository, UserDetailsService {

    @Autowired
    private PasswordEncoder bcryptEncoder;

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
        Profile newProfile = Profile.builder()
                .id(new ObjectId())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .email(profile.getEmail())
                .password(bcryptEncoder.encode(profile.getPassword()))
                .createdAt(profile.getCreatedAt())
                .build();
        profileCollection.insertOne(newProfile);
        return newProfile;
    }

    @Override
    public Optional<Profile> findByEmail(String email) {
        return Optional.ofNullable(profileCollection.find(eq("email", email)).first());
    }

    @Override
    public List<Profile> findAll() {
        return profileCollection.find().into(new ArrayList<>());
    }

    @Override
    public Profile findOne(String id) {
        return profileCollection.find(eq("_id", new ObjectId(id))).first();
    }

    @Override
    public long count() {
        return profileCollection.countDocuments();
    }

    @Override
    public long delete(String id) {
        return profileCollection.deleteOne(eq("_id", new ObjectId(id))).getDeletedCount();
    }

    @Override
    public long deleteAll() {
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(
                    () -> profileCollection.deleteMany(clientSession, new BsonDocument()).getDeletedCount(), txnOptions);
        }    }

    @Override
    public Profile update(Profile profile) {
        FindOneAndReplaceOptions options = new FindOneAndReplaceOptions().returnDocument(AFTER);
        return profileCollection.findOneAndReplace(eq("_id", profile.getId()), profile, options);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Profile> profile = findByEmail(email);
        if (!profile.isPresent()) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        return new User(profile.get().getEmail(), profile.get().getPassword(), new ArrayList<>());
    }
}
