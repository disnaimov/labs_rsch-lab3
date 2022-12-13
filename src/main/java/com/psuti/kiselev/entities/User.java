package com.psuti.kiselev.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Table(name = "users")
@Entity
@Getter
@Setter
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(nullable = false, length = 25,name = "first_name")
   private String firstname;

    @Column(nullable = false, length = 25,name = "last_name")
   private String lastname;

    @ManyToOne
    @JoinColumn(name = "role")
   private Role role;


    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @JsonIgnore
    private boolean enabled = true;

}
