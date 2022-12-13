package com.psuti.kiselev.dao;

import com.psuti.kiselev.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findUserByEmail(String email);
    void removeByEmail(String email);
}


