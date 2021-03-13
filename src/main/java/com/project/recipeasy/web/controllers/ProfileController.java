package com.project.recipeasy.web.controllers;

import com.project.recipeasy.web.models.LoginRequest;
import com.project.recipeasy.models.Profile;
import com.project.recipeasy.web.models.AuthResponse;
import com.project.recipeasy.repositories.ProfileRepository;
import com.project.recipeasy.web.config.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


@RestController
public class ProfileController {

    private final static Logger LOGGER = LoggerFactory.getLogger(ProfileController.class);
    private final ProfileRepository profileRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private ProfileRepository profileRespository;
    @Autowired
    private PasswordEncoder bcryptEncoder;

    public ProfileController(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<AuthResponse> register(@RequestBody Profile profile) throws Exception {
        try {
            Profile savedProfile = profileRespository.save(profile);
            authenticate(profile.getEmail(), profile.getPassword());
            UserDetails userDetails = profileRespository.loadUserByUsername(profile.getEmail());
            String token = jwtTokenUtil.generateToken(userDetails);
            AuthResponse authResponse = AuthResponse.builder()
                    .id(savedProfile.getId().toString())
                    .firstName(savedProfile.getFirstName())
                    .lastName(savedProfile.getLastName())
                    .email(savedProfile.getEmail())
                    .token(token)
                    .createdAt(savedProfile.getCreatedAt())
                    .build();
            return ResponseEntity.ok(authResponse);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error Registering User");
        }
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception {
        Optional<Profile> maybeProfile = profileRepository.findByEmail(loginRequest.getEmail());
        if (!maybeProfile.isPresent()) {
            return ResponseEntity.notFound().build();
        } else {
            Profile foundProfile = maybeProfile.get();
            if (!bcryptEncoder.matches(loginRequest.getPassword(), foundProfile.getPassword())) {
                throw new RuntimeException("Password does not match");
            } else {
                UserDetails userDetails = profileRespository.loadUserByUsername(foundProfile.getEmail());
                String token = jwtTokenUtil.generateToken(userDetails);
                return ResponseEntity.ok(AuthResponse.builder()
                        .id(foundProfile.getId().toString())
                        .firstName(foundProfile.getFirstName())
                        .lastName(foundProfile.getLastName())
                        .email(foundProfile.getEmail())
                        .createdAt(foundProfile.getCreatedAt())
                        .token(token)
                        .build());
            }
        }
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<Profile> getProfileById(@PathVariable String id) {
        Profile profile = profileRepository.findOne(id);
        if (profile == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/profiles")
    public List<Profile> getProfiles() {
        return profileRepository.findAll();
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

    private void authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
