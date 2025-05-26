-- =====================================================
-- 1)  TABLA DE ROLES
-- =====================================================
CREATE TABLE IF NOT EXISTS rol (
    id     SERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
);

INSERT INTO rol (nombre) VALUES
  ('administrador'),
  ('usuario')
ON CONFLICT (nombre) DO NOTHING;



-- =====================================================
-- 2)  TABLA DE TUTORES  (incluye la FK a ROL)
-- =====================================================
CREATE TABLE IF NOT EXISTS tutor (
    id          SERIAL PRIMARY KEY,
    nombre      VARCHAR(100) NOT NULL,
    correo      VARCHAR(100) NOT NULL UNIQUE,
    materia     VARCHAR(100) NOT NULL,
    contrasena  TEXT         NOT NULL,
    rol_id      INTEGER      NOT NULL
                 DEFAULT 2                      -- 2 = “usuario”
                 REFERENCES rol(id)
);

-- Usuario de prueba: administrador
INSERT INTO tutor (nombre, correo, materia, contrasena, rol_id)
VALUES ('Admin', 'admin@ues.edu.sv', 'General', '{noop}admin', 1)
ON CONFLICT (correo) DO NOTHING;



-- =====================================================
-- 3)  TABLA DE TAREAS  (vinculada a TUTOR)
-- =====================================================
/*
   Una tarea pertenece a un tutor (clave foránea tutor_id).
   • Al eliminar un tutor se eliminan sus tareas            → ON DELETE CASCADE
   • Si se actualiza el id del tutor (muy raro) se propaga  → ON UPDATE CASCADE
*/
CREATE TABLE IF NOT EXISTS tareas (
    id            SERIAL PRIMARY KEY,
    titulo        VARCHAR(255) NOT NULL,
    descripcion   TEXT,
    fecha_entrega DATE,
    categoria     VARCHAR(100),
    tutor_id      INTEGER      NOT NULL
                  REFERENCES tutor(id)
                  ON DELETE CASCADE
                  ON UPDATE CASCADE
);

-- Ejemplo de tarea para el usuario Admin (id = 1)
INSERT INTO tareas (titulo, descripcion, fecha_entrega, categoria, tutor_id)
VALUES (
    'Tarea Demo',
    'Descripción de ejemplo',
    CURRENT_DATE + INTERVAL '7 day',
    'General',
    1      -- tutor_id (Admin)
)
ON CONFLICT DO NOTHING;
