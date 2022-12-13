package com.psuti.kiselev.controller;

import com.psuti.kiselev.dto.AuthDto;
import com.psuti.kiselev.dto.RegDto;
import com.psuti.kiselev.entities.Token;
import com.psuti.kiselev.evenst.OnAuthorizationEvent;
import com.psuti.kiselev.evenst.OnRegistrationEvent;
import com.psuti.kiselev.service.UserAuthService;
import com.psuti.kiselev.service.UserRegService;
import com.psuti.kiselev.service.VerificationService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthRestController {
    private final UserAuthService userAuthService;
    private final UserRegService userRegService;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final VerificationService verificationService;
    public AuthRestController(UserAuthService userAuthService,
                              UserRegService userRegService,
                              ApplicationEventPublisher applicationEventPublisher, VerificationService verificationService) {
        this.userAuthService = userAuthService;
        this.userRegService = userRegService;
        this.applicationEventPublisher = applicationEventPublisher;
        this.verificationService = verificationService;
    }
    @PostMapping(path = "/login")
    @RequestMapping(value = "/login")
    public Token authorization(@RequestBody @Validated AuthDto authDto) throws Exception {
        applicationEventPublisher
                .publishEvent(new OnAuthorizationEvent(authDto));
        return userAuthService.authorization(authDto);
    }
    @PostMapping
    @RequestMapping("/reg")
    public Token registration(@RequestBody @Validated RegDto user) throws Exception {
        Token token = userRegService.registration(user);
        user.setId(token.getUserId());
        applicationEventPublisher.publishEvent(new OnRegistrationEvent(user));
        return token;
    }

    @PostMapping("/confirm")
    public void confirm(@RequestParam("token") String token){
        verificationService.confirm(token);
    }
}




