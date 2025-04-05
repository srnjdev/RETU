-- =====================================================
-- Ejemplo para crear la tabla 'tutor' si no existe,
-- y luego insertar un usuario de prueba.
-- =====================================================

CREATE TABLE IF NOT EXISTS tutor (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) NOT NULL,
    materia VARCHAR(100) NOT NULL,
    contrasena TEXT NOT NULL
);

-- Insertar usuario de prueba (admin@retu.com, pass: "admin")
INSERT INTO tutor (nombre, correo, materia, contrasena)
VALUES ('Admin', 'admin@ues.edu.sv', 'General', '{noop}admin');

