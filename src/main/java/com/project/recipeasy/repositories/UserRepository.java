package com.project.recipeasy.repositories;

import com.project.recipeasy.models.UserDTO;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {

    UserDTO save(UserDTO userDTO);

    UserDTO findByUsername(String username);
}
