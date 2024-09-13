package com.cibertec.adopta_una_mascota.repository;

import com.cibertec.adopta_una_mascota.entity.EliminarTok;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface EliminarRepositoryTok extends CrudRepository<EliminarTok, Long> {
    Optional<EliminarTok> findByToken(String token);
}
