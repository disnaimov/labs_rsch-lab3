package com.psuti.kiselev.entities;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
@Data
public class Role {

    @Id
    @Column(name = "name", nullable = false)
    private String name;
}