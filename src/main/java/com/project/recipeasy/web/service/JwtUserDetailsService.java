package com.project.recipeasy.web.service;

import com.project.recipeasy.models.Profile;
import com.project.recipeasy.repositories.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Profile profile = profileRepository.findByEmail(email);
        if (profile == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        return new User(profile.getEmail(), profile.getPassword(), new ArrayList<>());
    }

    public Profile save(Profile profile) {
        Profile newProfile = new Profile();
        newProfile.setFirstName(profile.getFirstName());
        newProfile.setLastName(profile.getLastName());
        newProfile.setEmail(profile.getEmail());
        newProfile.setPassword(bcryptEncoder.encode(profile.getPassword()));
        return profileRepository.save(newProfile);
    }
}
