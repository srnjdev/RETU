package com.retu.retu.config;

import com.retu.retu.repository.TutorRepository;
import com.retu.retu.service.CustomOAuth2UserService;
import com.retu.retu.service.CustomUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Configuration
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    // Necesario para crear el CustomOAuth2UserService
    @Autowired
    private TutorRepository tutorRepository;

    // Definir el bean para el servicio OAuth2 personalizado
    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> customOAuth2UserService() {
        return new CustomOAuth2UserService(tutorRepository);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());

        http.authorizeHttpRequests(auth -> auth
            .requestMatchers("/login", "/css/**", "/js/**", "/imagenes/**").permitAll()
            .requestMatchers("/tutores/**").hasRole("ADMIN")
            .requestMatchers("/tareas/**").hasAnyRole("ADMIN", "USUARIO")
            .anyRequest().authenticated()
        );

        http.formLogin(form -> form
            .loginPage("/login")
            .defaultSuccessUrl("/home", true)
            .permitAll()
        );

        // Agregamos userService para manejar el registro automático
        http.oauth2Login(oauth2 -> oauth2
            .loginPage("/login")
            .userInfoEndpoint(userInfo -> userInfo
                .userService(customOAuth2UserService())
            )
            .defaultSuccessUrl("/home", true)
        );

        http.logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login?logout")
            .permitAll()
        );

        http.exceptionHandling(exception -> exception
            .accessDeniedHandler((request, response, accessDeniedException) -> {
                response.sendRedirect("/home?denegado");
            })
        );

        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authBuilder
            .userDetailsService(userDetailsService)
            .passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());

        return authBuilder.build();
    }
}