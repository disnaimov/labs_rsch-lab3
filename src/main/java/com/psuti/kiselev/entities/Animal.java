package com.psuti.kiselev.entities;


import lombok.Getter;
import lombok.Setter;

import javax.management.relation.Role;
import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Table(name = "animals")
@Entity
@Getter
@Setter
public class Animal implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "animal_id", nullable = false) //name = "animal"
    private UUID id;  // UUID id;

    @Column(nullable = false, length = 25,name = "animal_name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "kind")
    private Kind kind;

}
