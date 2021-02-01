package com.project.recipeasy;

import com.project.recipeasy.models.*;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;

@Component
class TestHelper {

    public Profile getProfile() {
        return Profile.builder()
                .firstName("Tom")
                .lastName("Smith")
                .build();
    }
}
