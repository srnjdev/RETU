package com.retu.retu.repository;

import com.retu.retu.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Rol, Long> {
    Rol findByNombre(String nombre); // necesario para OAuth
}
