package com.cibertec.adopta_una_mascota.service;

import com.cibertec.adopta_una_mascota.entity.EliminarTok;
import com.cibertec.adopta_una_mascota.repository.EliminarRepositoryTok;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TokenService {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private static EliminarRepositoryTok eliminarRepositoryTok;

    public void eliminarToken(String token) {
        logger.info("Attempting to revoke token: {}", token);
        EliminarTok eliminarTok = new EliminarTok();
        eliminarTok.setToken(token);
        eliminarTok.setRevokedAt(LocalDateTime.now());
        eliminarRepositoryTok.save(eliminarTok);
        logger.info("Token revoked and saved: {}", token);
    }

    public static boolean isTokenRemoved(String token) {
        boolean isRemoved = eliminarRepositoryTok.findByToken(token).isPresent();
        logger.info("Checking if token is removed: {}. Result: {}", token, isRemoved);
        return isRemoved;
    }
}

