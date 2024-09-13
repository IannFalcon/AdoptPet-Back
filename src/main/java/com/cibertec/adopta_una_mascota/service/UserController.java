package com.cibertec.adopta_una_mascota.service;

import com.cibertec.adopta_una_mascota.entity.UserClass;
import com.cibertec.adopta_una_mascota.repository.UserRepository;
import com.cibertec.adopta_una_mascota.response.LoginResponse;
import com.cibertec.adopta_una_mascota.security.JWTAuthenticationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/AdoptPet")
public class UserController {

    // Inyeccion de dependencias
    @Autowired
    UserRepository userRepository;

    @Autowired
    JWTAuthenticationConfig jwtAuthenticationConfig;

    // Metodo para iniciar sesión
    @PostMapping("/login")
    public LoginResponse IniciarSesion(@RequestBody UserClass user){

        UserClass usuarioObtendido = userRepository.findUserByDocNumber(user.getDocNumber());

        if(usuarioObtendido == null) {
            return new LoginResponse("99", "Autenticación fallida", null);
        }

        if(new BCryptPasswordEncoder().matches(user.getPassword(), usuarioObtendido.getPassword())){
            return new LoginResponse("99", "Autenticación fallida", null);
        }

        String token = jwtAuthenticationConfig.getJWTToken(usuarioObtendido.getDocNumber());
        return new LoginResponse("01", "Inicio de sesión exitoso.", token);

    }

}
