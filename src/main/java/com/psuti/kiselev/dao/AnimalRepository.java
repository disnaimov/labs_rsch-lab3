package com.psuti.kiselev.dao;

import com.psuti.kiselev.entities.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AnimalRepository extends JpaRepository<Animal, UUID> {
}
