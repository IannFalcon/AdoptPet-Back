package com.cibertec.adopta_una_mascota.repository;

import com.cibertec.adopta_una_mascota.entity.UserClass;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserClass, Integer> {

    // Buscar usuario por numero de documento
    UserClass findUserByDocNumber(String docNumber);

}
