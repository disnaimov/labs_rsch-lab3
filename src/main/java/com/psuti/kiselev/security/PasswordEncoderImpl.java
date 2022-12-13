package com.psuti.kiselev.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoderImpl implements PasswordEncoder {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Value("${jwt.secret}")
    private String salt;
    public PasswordEncoderImpl() {
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
    }
    @Override
    public String encode(CharSequence rawPassword) {
        return bCryptPasswordEncoder.encode(rawPassword+salt);
    }
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return bCryptPasswordEncoder.matches(rawPassword+salt,encodedPassword);
    }
}
