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

CREATE TABLE IF NOT EXISTS rol (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
)

INSERT INTO rol (nombre) VALUES 
('administrador'),
('usuario')
ON CONFLICT (nombre) DO NOTHING;

ALTER TABLE tutor ADD COLUMN rol_id BIGINT;
ALTER TABLE tutor ADD CONSTRAINT fk_tutor_rol FOREIGN KEY (rol_id) REFERENCES rol(id) NOT NULL DEFAULT 2;


-- Insertar usuario de prueba (admin@retu.com, pass: "admin")
INSERT INTO tutor (nombre, correo, materia, contrasena, rol_id)
VALUES ('Admin', 'admin@ues.edu.sv', 'General', '{noop}admin', 1);

