-- =========================
-- 1) Tabla de USUARIOS
-- =========================
CREATE TABLE IF NOT EXISTS usuarios (
    id              SERIAL PRIMARY KEY,
    username        VARCHAR(50) NOT NULL UNIQUE,
    password        VARCHAR(100) NOT NULL,
    nombre_completo VARCHAR(100),
    email           VARCHAR(100),
    rol             VARCHAR(20) DEFAULT 'ESTUDIANTE'
);

-- =========================
-- 2) Tabla de ASIGNATURAS
-- =========================
CREATE TABLE IF NOT EXISTS asignaturas (
    id      SERIAL PRIMARY KEY,
    nombre  VARCHAR(100) NOT NULL
);


-- =========================
-- 3) Tabla de TAREAS
-- =========================
CREATE TABLE IF NOT EXISTS tareas (
    id            SERIAL PRIMARY KEY,
    titulo        VARCHAR(100) NOT NULL,
    descripcion   TEXT,
    fecha         DATE NOT NULL,
    prioridad     VARCHAR(10) NOT NULL,
    asignatura_id INT NOT NULL,
    CONSTRAINT fk_asignatura
        FOREIGN KEY(asignatura_id) 
        REFERENCES asignaturas(id)
        ON DELETE CASCADE
);

