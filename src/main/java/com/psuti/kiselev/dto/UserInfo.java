package com.psuti.kiselev.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo implements Serializable {
    private UUID id;
    private String firstname;
    private String lastname;
    private String email;
}
