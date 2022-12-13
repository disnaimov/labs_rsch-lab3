package com.psuti.kiselev.service;


import com.psuti.kiselev.dao.TokenRepository;
import com.psuti.kiselev.entities.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class JwtTokenService {
    private final JwtTokenUtil<UUID> jwtUserIdTokenUtil;
    private final TokenRepository tokenRepository;
    @Autowired
    public JwtTokenService(JwtTokenUtil<UUID> jwtUserIdTokenUtil,
                           TokenRepository tokenRepository) {
        this.jwtUserIdTokenUtil = jwtUserIdTokenUtil;
        this.tokenRepository = tokenRepository;
    }
    public Token createToken(UUID userId){
        String token = jwtUserIdTokenUtil.generateToken(userId);
        Token tokenEntity = new Token();
        tokenEntity.setUserId(userId);
        tokenEntity.setValue(token);
        tokenEntity.setKilled(false);
        return tokenRepository.save(tokenEntity);
    }
    public void kill(String tokenValue){
        Token token = tokenByValue(tokenValue);
        token.setKilled(true);
        tokenRepository.save(token);
    }
    public void kill(Token token){
        token.setKilled(true);
        tokenRepository.save(token);
    }
    public boolean tokenIsKilled(String tokenValue){
        return tokenByValue(tokenValue).isKilled();
    }
    public boolean tokenExists(String tokenValue){
        AtomicBoolean exists = new AtomicBoolean(false);
        tokenRepository.findByValue(tokenValue).ifPresent(
                (t)-> exists.set(!t.isKilled())
        );
        return exists.get();
    }
    private Token tokenByValue(String val){
        Token token = tokenRepository.findByValue(val).orElseThrow(()->{
            throw new EntityExistsException("token with value: '" + val + "' doesn't exists");
        });
        return token;
    }
    public Optional<Token> findOptionalByUserId(UUID id){
        return tokenRepository.findTokenByUserId(id);
    }
    public Token findByUserId(UUID id){
        return tokenRepository.findTokenByUserId(id).orElseThrow(()->{
            throw new EntityExistsException("token by user id: " + id + " doesn't exists");
        });
    }
    public boolean validateToken(String token, UUID id){
        if(!jwtUserIdTokenUtil.validateToken(token,id)){
            if(new Date().before(jwtUserIdTokenUtil.getExpirationDateFromToken(token))){
                kill(token);
            }
            return false;
        }
        return true;
    }
    public UUID getIdFromToken(String token){
        return jwtUserIdTokenUtil.getSubjectFromToken(token);
    }
    public Token refreshToken(String tokenValue){
        Token token = tokenByValue(tokenValue);
        token.setValue(jwtUserIdTokenUtil.generateToken(token.getUserId()));
        return tokenRepository.save(token);
    }
}

