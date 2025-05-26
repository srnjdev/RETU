package com.retu.retu;

import com.retu.retu.entity.Rol;
import com.retu.retu.repository.RolRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RetuApplication {

	public static void main(String[] args) {
		SpringApplication.run(RetuApplication.class, args);
	}

	@Bean
	public CommandLineRunner initRoles(RolRepository rolRepository) {
		return args -> {
			if (rolRepository.findByNombre("ADMIN") == null) {
				rolRepository.save(new Rol("ADMIN"));
			}
			if (rolRepository.findByNombre("USUARIO") == null) {
				rolRepository.save(new Rol("USUARIO"));
			}
		};
	}
}
