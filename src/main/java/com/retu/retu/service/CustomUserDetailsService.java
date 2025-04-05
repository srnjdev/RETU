package com.retu.retu.service;

import com.retu.retu.entity.Tutor;
import com.retu.retu.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;

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

        // Contraseña en texto plano, tal cual está en BD
        String password = tutor.getContrasena();

        // Rol genérico "ROLE_USER"
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");

        return new User(
                tutor.getCorreo(),   // "username"
                password,            // "password" (texto plano)
                Collections.singletonList(authority)
        );
    }
}
