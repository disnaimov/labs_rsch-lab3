package com.psuti.kiselev.security;

import com.psuti.kiselev.entities.User;
import com.psuti.kiselev.service.UserCrudService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserCrudService userCrudService;
    public UserDetailsServiceImpl(UserCrudService userCrudService){
        this.userCrudService = userCrudService;
    }
    public UserDetails loadUserById(UUID id){
        return fromEntity(userCrudService.getById(id));
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return fromEntity(userCrudService.getByUsername(username));
    }
    UserDetails fromEntity(User user){
        boolean enabled = user.isEnabled();
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                enabled,enabled,enabled,enabled,
                grantedAuthorities(user)
        );
    }
    public static List<GrantedAuthority> grantedAuthorities(User user){
        return Collections.singletonList(new SimpleGrantedAuthority(
                "ROLE_" + user.getRole().getName())
        );
    }
}
