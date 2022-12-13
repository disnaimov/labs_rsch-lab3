package com.psuti.kiselev.service;

import com.psuti.kiselev.dto.AuthDto;
import com.psuti.kiselev.entities.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class UserAuthService {
    private final UserCrudService userCrudService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtTokenService jwtTokenService;
    @Autowired
    public UserAuthService(UserCrudService userCrudService,
                           AuthenticationManager authenticationManager,
                           UserDetailsService userDetailsService,
                           JwtTokenService jwtTokenService) {
        this.userCrudService = userCrudService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenService = jwtTokenService;
    }
    public Token authorization(AuthDto authDto) throws Exception {
        authenticate(authDto);
        return getToken(authDto.getEmail());
    }
    private void authenticate(AuthDto data) throws Exception {
        try {
            UserDetails user = userDetailsService
                    .loadUserByUsername(data.getEmail());
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            data.getEmail(),
                            data.getPassword(),
                            user.getAuthorities()
                    )
            );
            System.out.println(SecurityContextHolder.getContext().getAuthentication());
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
    private Token getToken(String username) {
        UUID userId = userCrudService.getByUsername(username).getId();
        AtomicReference<Token> token = new AtomicReference<>();
        jwtTokenService.findOptionalByUserId(userId).ifPresentOrElse(
                (t) -> {
                    if (t.isKilled()) {
                        token.set(jwtTokenService.createToken(userId));
                    } else {
                        token.set(t);
                    }
                },
                () -> token.set(jwtTokenService.createToken(userId))
        );
        return token.get();
    }
}


