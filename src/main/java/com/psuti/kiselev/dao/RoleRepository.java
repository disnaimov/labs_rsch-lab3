package com.psuti.kiselev.dao;

import com.psuti.kiselev.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
}
