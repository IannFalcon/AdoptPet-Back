package com.cibertec.adopta_una_mascota.repository;

import com.cibertec.adopta_una_mascota.entity.UserClass;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<UserClass, Integer> {

    // Buscar usuario por numero de documento
    List<UserClass> findUserByDocNumber(String docNumber);

}
