package com.psuti.kiselev.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name ="kinds")
public class Kind implements Serializable {
   @Id
    @Column(name = "name", nullable = false)
    private String name;
}
