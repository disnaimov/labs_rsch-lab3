package com.psuti.kiselev.service;

import com.psuti.kiselev.dao.RoleRepository;
import com.psuti.kiselev.dto.AuthDto;
import com.psuti.kiselev.dto.RegDto;
import com.psuti.kiselev.entities.Token;
import com.psuti.kiselev.entities.User;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;

@Service
public class UserRegService {
    private final UserCrudService userCrudService;
    private final RoleRepository roleRepository;
    private final UserAuthService userAuthService;
    public UserRegService(UserCrudService userCrudService,
                          RoleRepository roleRepository,
                          UserAuthService userAuthService) {
        this.userCrudService = userCrudService;
        this.roleRepository = roleRepository;
        this.userAuthService = userAuthService;
    }
    public Token registration(RegDto regDto) throws Exception {
        if (userCrudService.existsByUsername(regDto.getEmail())) {
            throw new EntityExistsException("User with email: " + regDto.getEmail() + " already exists");
        }
        User user = fromRegDto(regDto);
        System.out.println("user = " + user);
        user.setRole(roleRepository.findById("USER").get());
        userCrudService.create(user);
        Token token = userAuthService.authorization(
                new AuthDto(user.getEmail(), regDto.getPassword())
        );
        return token;
    }
    private User fromRegDto(RegDto regDto){
        User user = new User();
        user.setEnabled(true);
        user.setEmail(regDto.getEmail());
        user.setFirstname(regDto.getFirstname());
        user.setLastname(regDto.getLastname());
        user.setPassword(regDto.getPassword());
        return user;
    }
}

