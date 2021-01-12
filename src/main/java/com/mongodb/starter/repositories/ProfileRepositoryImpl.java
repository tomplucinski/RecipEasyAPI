package com.mongodb.starter.repositories;

import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.TransactionOptions;
import com.mongodb.WriteConcern;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.ReplaceOneModel;
import com.mongodb.client.model.WriteModel;
import com.mongodb.starter.models.Profile;
import org.bson.BsonDocument;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;
import static com.mongodb.client.model.ReturnDocument.AFTER;

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
        profile.setId(new ObjectId());
        profileCollection.insertOne(profile);
        return profile;
    }

    @Override
    public List<Profile> saveAll(List<Profile> profiles) {
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(() -> {
                profiles.forEach(p -> p.setId(new ObjectId()));
                profileCollection.insertMany(clientSession, profiles);
                return profiles;
            }, txnOptions);
        }
    }

    @Override
    public List<Profile> findAll() {
        return profileCollection.find().into(new ArrayList<>());
    }

    @Override
    public List<Profile> findAll(List<String> ids) {
        return profileCollection.find(in("_id", mapToObjectIds(ids))).into(new ArrayList<>());
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
    public long delete(List<String> ids) {
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(
                    () -> profileCollection.deleteMany(clientSession, in("_id", mapToObjectIds(ids))).getDeletedCount(),
                    txnOptions);
        }    }

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
    public long update(List<Profile> profiles) {
        List<WriteModel<Profile>> writes = profiles.stream()
                .map(p -> new ReplaceOneModel<>(eq("_id", p.getId()), p))
                .collect(Collectors.toList());
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(
                    () -> profileCollection.bulkWrite(clientSession, writes).getModifiedCount(), txnOptions);
        }    }

    private List<ObjectId> mapToObjectIds(List<String> ids) {
        return ids.stream().map(ObjectId::new).collect(Collectors.toList());
    }
}
