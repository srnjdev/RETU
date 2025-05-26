package com.retu.retu.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.retu.retu.entity.Tutor;
import com.retu.retu.repository.TutorRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private TutorRepository tutorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Tutor tutor = tutorRepository.findByCorreo(username);
        if (tutor == null) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }

        String password = tutor.getContrasena();

        String nombreRol = tutor.getRol() != null ? tutor.getRol().getNombre() : "";

        // Convertimos "Administrador" → "ROLE_ADMIN", "Usuario" → "ROLE_USUARIO"
        String springRole = switch (nombreRol.toUpperCase()) {
            case "ADMINISTRADOR" -> "ROLE_ADMIN";
            case "USUARIO" -> "ROLE_USUARIO";
            default -> throw new IllegalStateException("Rol desconocido: " + nombreRol);
        };

        return new User(
                tutor.getCorreo(),
                password,
                Collections.singletonList(new SimpleGrantedAuthority(springRole))
        );
    }
}
