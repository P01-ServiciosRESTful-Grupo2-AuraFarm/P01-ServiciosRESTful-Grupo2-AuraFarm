DROP SCHEMA public CASCADE;
CREATE SCHEMA public;


-- =============================================================================
-- 1. ADMINISTRADORES (Campos propios + heredados de Actor y DomainEntity)
-- =============================================================================
INSERT INTO administrador (id, version, nombre_usuario, fotografia, correo_electronico, contrasena) VALUES
(1, 0, 'admin_principal', 'https://picsum.photos/200/200?random=1', 'admin1@aurafarm.com', 'adminpass123'),
(2, 0, 'admin_soporte', NULL, 'admin2@aurafarm.com', 'adminpass123');

-- =============================================================================
-- 2. USUARIOS (Campos propios + victorias + heredados de Actor y DomainEntity)
-- =============================================================================
INSERT INTO usuario (id, version, nombre_usuario, fotografia, correo_electronico, contrasena, victorias) VALUES
(3, 0, 'aura_master', 'https://picsum.photos/200/200?random=3', 'jugador1@aurafarm.com', 'userpass123', 2),
(4, 0, 'shadow_farmer', 'https://picsum.photos/200/200?random=4', 'jugador2@aurafarm.com', 'userpass123', 1),
(5, 0, 'valkyrie_x', NULL, 'jugador3@aurafarm.com', 'userpass123', 0),
(6, 0, 'cyber_samurai', 'https://picsum.photos/200/200?random=6', 'jugador4@aurafarm.com', 'userpass123', 0);

-- =============================================================================
-- 3. JORNADAS (IDs 7 y 8)
-- =============================================================================
INSERT INTO jornada (id, version, nombre, descripcion, fecha, hora_inicio, hora_fin, max_inscripciones, plazo) VALUES
(7, 0, 'Gran Torneo de Primavera', 'Jornada clasificatoria de aura nivel 1.', '2026-11-15', '10:00:00', '14:00:00', 4, 'ABIERTO'),
(8, 0, 'Copa Nocturna Aura', 'Enfrentamientos rápidos nocturnos.', '2026-10-20', '21:00:00', '23:30:00', 4, 'CERRADO');

-- =============================================================================
-- 4. ENFRENTAMIENTOS (IDs 9, 10 y 11)
-- =============================================================================
INSERT INTO enfrentamiento (id, version, estado, jornada_id, jugador1_id, jugador2_id, ganador_id) VALUES
(9, 0, 'FINALIZADA', 8, 3, 4, 3),
(10, 0, 'FINALIZADA', 8, 5, 6, 4),
(11, 0, 'NO_REALIZADA', 8, 3, NULL, NULL);

-- =============================================================================
-- 5. NOTICIAS (IDs 12 y 13)
-- =============================================================================
INSERT INTO noticias (id, version, titulo, contenido, fecha_publicacion, imagen) VALUES
(12, 0, '¡Bienvenidos a AuraFarm!', 'Inauguramos oficialmente la plataforma de quedadas y torneos de aura.', '2026-10-01 09:00:00', 'https://picsum.photos/800/400?random=12'),
(13, 0, 'Aviso: Apertura de la Jornada de Primavera', 'Ya se encuentran abiertas las inscripciones para la próxima jornada.', '2026-10-15 12:00:00', NULL);

-- =============================================================================
-- 6. TABLAS INTERMEDIAS DE RELACIONES
-- =============================================================================
INSERT INTO usuario_jornadas (usuario_id, jornadas_id) VALUES
(3, 7), (4, 7), (3, 8), (4, 8), (5, 8);

INSERT INTO jornada_enfrentamientos (jornada_id, enfrentamientos_id) VALUES
(8, 9), (8, 10), (8, 11);

INSERT INTO usuario_enfrentamientos (usuario_id, enfrentamientos_id) VALUES
(3, 9), (4, 9), (5, 10), (6, 10), (3, 11);

INSERT INTO usuario_enfrentamientos_ganados (usuario_id, enfrentamientos_ganados_id) VALUES
(3, 9), (4, 10);

-- =============================================================================
-- 7. REAJUSTE DE LA SECUENCIA DE IDs PARA POSTGRESQL
-- =============================================================================
SELECT setval('domain_entity_id_seq', (
    SELECT MAX(id) FROM (
        SELECT id FROM administrador
        UNION ALL SELECT id FROM usuario
        UNION ALL SELECT id FROM jornada
        UNION ALL SELECT id FROM enfrentamiento
        UNION ALL SELECT id FROM noticias
    ) AS todos_los_ids
));