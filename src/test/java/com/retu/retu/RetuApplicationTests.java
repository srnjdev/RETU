package com.retu.retu;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest(classes = RetuApplication.class) // Especificamos la clase principal
@ActiveProfiles("test") // Activamos el perfil de pruebas
@WithMockUser(username = "testuser", roles = "USER") // Simulamos un usuario autenticado
class RetuApplicationTests {

    @Test
    void contextLoads() {
        // Solo verificamos que el contexto cargue correctamente
    }
}

