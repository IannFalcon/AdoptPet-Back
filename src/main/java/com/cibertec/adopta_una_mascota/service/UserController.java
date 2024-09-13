package com.cibertec.adopta_una_mascota.service;

import com.cibertec.adopta_una_mascota.entity.UserClass;
import com.cibertec.adopta_una_mascota.repository.UserRepository;
import com.cibertec.adopta_una_mascota.response.LoginResponse;
import com.cibertec.adopta_una_mascota.response.LogoutResponse;
import com.cibertec.adopta_una_mascota.security.JWTAuthenticationConfig;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/AdoptPet")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTAuthenticationConfig jwtAuthenticationConfig;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> iniciarSesion(@RequestBody UserClass user) {
        if (user.getDocNumber() == null || user.getPassword() == null) {
            return ResponseEntity.badRequest().body(new LoginResponse("99", "Datos de inicio de sesión incompletos", null));
        }

        UserClass usuarioObtenido = userRepository.findUserByDocNumber(user.getDocNumber());
        if (usuarioObtenido == null) {
            return ResponseEntity.ok(new LoginResponse("99", "Usuario no encontrado", null));
        }

        if (new BCryptPasswordEncoder().matches(user.getPassword(), usuarioObtenido.getPassword())) {
            logger.warn("Intento de inicio de sesión fallido para el usuario: {}", user.getDocNumber());
            return ResponseEntity.ok(new LoginResponse("99", "Contraseña incorrecta", null));
        }

        logger.info("Inicio de sesión exitoso para el usuario: {}", user.getDocNumber());
        String token = jwtAuthenticationConfig.getJWTToken(usuarioObtenido.getDocNumber());
        return ResponseEntity.ok(new LoginResponse("01", "Inicio de sesión exitoso", token));
    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutResponse> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            TokenService tokenService= new TokenService();
            tokenService.eliminarToken(token);
            logger.info("Cierre de sesión exitoso para el token: {}", token);
            return ResponseEntity.ok(new LogoutResponse("01", "Cierre de sesión exitoso"));
        }
        return ResponseEntity.badRequest().body(new LogoutResponse("99", "Token no proporcionado"));
    }
}