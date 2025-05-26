package com.retu.retu.service;

import java.util.List;
import java.util.Optional;

import com.retu.retu.entity.Rol;
import com.retu.retu.entity.Tutor;
import com.retu.retu.repository.RolRepository;
import com.retu.retu.repository.TutorRepository;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final TutorRepository tutorRepository;
    private final RolRepository rolRepository;

    public CustomOAuth2UserService(TutorRepository tutorRepository, RolRepository rolRepository) {
        this.tutorRepository = tutorRepository;
        this.rolRepository = rolRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oauthUser = super.loadUser(userRequest);

        // ⚠️ Validaciones básicas
        String email = oauthUser.getAttribute("email");
        String nombre = oauthUser.getAttribute("name");

        if (email == null || nombre == null) {
            throw new RuntimeException("El usuario de OAuth2 no contiene 'email' o 'name'");
        }

        Optional<Tutor> optionalTutor = tutorRepository.findByCorreo(email);

        if (optionalTutor.isEmpty()) {
            Tutor nuevoTutor = new Tutor();
            nuevoTutor.setNombre(nombre);
            nuevoTutor.setCorreo(email);
            nuevoTutor.setMateria("General"); // Valor por defecto
            nuevoTutor.setContrasena("oauth2"); // No se usará, pero el campo es obligatorio

            // 👇 Buscar rol por nombre
            Rol rolUsuario = rolRepository.findByNombre("USUARIO");

            if (rolUsuario == null) {
                throw new RuntimeException("No existe el rol 'USUARIO' en la base de datos");
            }

            nuevoTutor.setRol(rolUsuario);
            tutorRepository.save(nuevoTutor);
        }

        return new DefaultOAuth2User(
                List.of(new SimpleGrantedAuthority("ROLE_USUARIO")),
                oauthUser.getAttributes(),
                "email"
        );
    }
}

