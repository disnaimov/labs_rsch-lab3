package com.psuti.kiselev.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class LogoutService {
    private final TokenParserFromRequest tokenParserFromRequest;
    private final JwtTokenService jwtTokenService;
    @Autowired
    public LogoutService(TokenParserFromRequest tokenParserFromRequest,
                         JwtTokenService jwtTokenService) {
        this.tokenParserFromRequest = tokenParserFromRequest;
        this.jwtTokenService = jwtTokenService;
    }
    public void logout(HttpServletRequest request){
        String token = tokenParserFromRequest.parse(request);
        jwtTokenService.kill(token);
    }
    public void logout(String token){
        jwtTokenService.kill(token);
    }
}

