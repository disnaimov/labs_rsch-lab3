package com.psuti.kiselev.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegDto extends AuthDto {
    private String firstname;
    private String lastname;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;
}


