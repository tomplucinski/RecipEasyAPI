package com.project.recipeasy;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.project.recipeasy.models.Profile;
import com.project.recipeasy.repositories.ProfileRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProfileControllerIT {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private TestHelper testHelper;
    private String URL;

    @Autowired
    ProfileControllerIT(MongoClient mongoClient) {
        createProfileCollectionIfNotPresent(mongoClient);
    }

    @PostConstruct
    void setUp() {
        URL = "http://localhost:" + port + "/api";
    }

    @AfterEach
    void tearDown() {
        profileRepository.deleteAll();
    }

    @DisplayName("POST /Profile with 1 profile")
    @Test
    void postProfile() {
        // GIVEN
        // WHEN
        ResponseEntity<Profile> result = restTemplate.postForEntity(URL + "/profile", testHelper.getProfile(), Profile.class);
        // THEN
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Profile profileResult = result.getBody();
        assertThat(profileResult.getId()).isNotNull();
        assertThat(profileResult).isEqualToIgnoringGivenFields(testHelper.getProfile(), "id", "createdAt");
    }

    private void createProfileCollectionIfNotPresent(MongoClient mongoClient) {
        // This is required because it is not possible to create a new collection within a multi-documents transaction.
        // Some tests start by inserting 2 documents with a transaction.
        MongoDatabase db = mongoClient.getDatabase("recipeasy");
        if (!db.listCollectionNames().into(new ArrayList<>()).contains("profiles"))
            db.createCollection("profiles");
    }
}
