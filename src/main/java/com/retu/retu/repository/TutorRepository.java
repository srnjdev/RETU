package com.retu.retu.repository;

import com.retu.retu.entity.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TutorRepository extends JpaRepository<Tutor, Long> {
    Tutor findByCorreo(String correo);
}
