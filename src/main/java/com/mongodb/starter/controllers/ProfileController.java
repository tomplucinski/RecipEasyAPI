package com.mongodb.starter.controllers;

import com.mongodb.starter.models.Profile;
import com.mongodb.starter.repositories.ProfileRepository;
import com.mongodb.starter.repositories.ProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Arrays.asList;

@RestController
@RequestMapping("/api")
public class ProfileController {

    private final static Logger LOGGER = LoggerFactory.getLogger(ProfileController.class);
    private final ProfileRepository profileRepository;

    public ProfileController(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @PostMapping("profile")
    @ResponseStatus(HttpStatus.CREATED)
    public Profile postProfile(@RequestBody Profile profile) {
        return profileRepository.save(profile);
    }

    @PostMapping("profiles")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Profile> postProfiles(@RequestBody List<Profile> profiles) {
        return profileRepository.saveAll(profiles);
    }

    @GetMapping("profiles")
    public List<Profile> getProfiles() {
        return profileRepository.findAll();
    }

    @GetMapping("profile/{id}")
    public ResponseEntity<Profile> getProfile(@PathVariable String id) {
        Profile profile = profileRepository.findOne(id);
        if (profile == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(profile);
    }

    @GetMapping("profiles/{ids}")
    public List<Profile> getProfiles(@PathVariable String ids) {
        List<String> listIds = asList(ids.split(","));
        return profileRepository.findAll(listIds);
    }

    @GetMapping("profiles/count")
    public Long getCount() {
        return profileRepository.count();
    }

    @DeleteMapping("profile/{id}")
    public Long deleteProfile(@PathVariable String id) {
        return profileRepository.delete(id);
    }

    @DeleteMapping("profiles/{ids}")
    public Long deleteProfiles(@PathVariable String ids) {
        List<String> listIds = asList(ids.split(","));
        return profileRepository.delete(listIds);
    }

    @DeleteMapping("profiles")
    public Long deleteProfiles() {
        return profileRepository.deleteAll();
    }

    @PutMapping("profile")
    public Profile putProfile(@RequestBody Profile profile) {
        return profileRepository.update(profile);
    }

    @PutMapping("profiles")
    public Long putProfile(@RequestBody List<Profile> profiles) {
        return profileRepository.update(profiles);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Exception handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return e;
    }
}
