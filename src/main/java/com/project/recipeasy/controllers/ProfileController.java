package com.project.recipeasy.controllers;

import com.project.recipeasy.models.Profile;
import com.project.recipeasy.repositories.ProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProfileController {

    private final static Logger LOGGER = LoggerFactory.getLogger(ProfileController.class);
    private final ProfileRepository profileRepository;

    public ProfileController(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @PostMapping("/profile")
    @ResponseStatus(HttpStatus.CREATED)
    public Profile createProfile(@RequestBody Profile profile) {
        return profileRepository.save(profile);
    }

    @GetMapping("/profiles")
    public List<Profile> getProfiles() {
        return profileRepository.findAll();
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<Profile> getProfileById(@PathVariable String id) {
        Profile profile = profileRepository.findOne(id);
        if (profile == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/profiles/count")
    public Long getProfilesCount() {
        return profileRepository.count();
    }

    @DeleteMapping("/profile/{id}")
    public Long deleteProfile(@PathVariable String id) {
        return profileRepository.delete(id);
    }

    @DeleteMapping("/profiles")
    public Long deleteAllProfiles() {
        return profileRepository.deleteAll();
    }

    @PutMapping("/profile")
    public Profile updateProfile(@RequestBody Profile profile) {
        return profileRepository.update(profile);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Exception handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return e;
    }
}
