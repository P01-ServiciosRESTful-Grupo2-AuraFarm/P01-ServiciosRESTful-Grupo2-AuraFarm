INSERT INTO administrador (id, version, nombre_usuario, foto, email, contrasena) VALUES
(1, 0, 'admin_principal', 'https://picsum.photos/200/200?random=1', 'admin1@aurafarm.com', 'adminpass123'),
(2, 0, 'admin_soporte', NULL, 'admin2@aurafarm.com', 'adminpass123');

INSERT INTO usuario (id, version, nombre_usuario, foto, email, contrasena, victorias) VALUES
(3, 0, 'aura_master', 'https://picsum.photos/200/200?random=3', 'jugador1@aurafarm.com', 'userpass123', 2),
(4, 0, 'shadow_farmer', 'https://picsum.photos/200/200?random=4', 'jugador2@aurafarm.com', 'userpass123', 1),
(5, 0, 'valkyrie_x', NULL, 'jugador3@aurafarm.com', 'userpass123', 0),
(6, 0, 'cyber_samurai', 'https://picsum.photos/200/200?random=6', 'jugador4@aurafarm.com', 'userpass123', 0);

INSERT INTO jornada (id, version, nombre, descripcion, fecha, hora_inicio, hora_fin, max_inscripciones, plazo) VALUES
(7, 0, 'Gran Torneo de Primavera', 'Jornada clasificatoria de aura nivel 1.', '2026-11-15', '10:00:00', '14:00:00', 4, 0),
(8, 0, 'Copa Nocturna Aura', 'Enfrentamientos rapidos nocturnos.', '2026-10-20', '21:00:00', '23:30:00', 4, 1);

INSERT INTO enfrentamiento (id, version, estado) VALUES
(9, 0, 1),
(10, 0, 1),
(11, 0, 2);

INSERT INTO noticias (id, version, titulo, contenido, fecha_publicacion, imagen) VALUES
(12, 0, 'Bienvenidos a AuraFarm', 'Inauguramos oficialmente la plataforma de quedadas y torneos de aura.', '2026-10-01', 'https://picsum.photos/800/400?random=12'),
(13, 0, 'Aviso: Apertura de la Jornada de Primavera', 'Ya se encuentran abiertas las inscripciones para la proxima jornada.', '2026-10-15', NULL);

INSERT INTO usuario_jornadas (usuario_id, jornadas_id) VALUES
(3, 7), (4, 7), (3, 8), (4, 8), (5, 8);

INSERT INTO jornada_enfrentamientos (jornada_id, enfrentamientos_id) VALUES
(8, 9), (8, 10), (8, 11);

INSERT INTO usuario_enfrentamientos (usuario_id, enfrentamientos_id) VALUES
(3, 9), (4, 9), (5, 10), (6, 10), (3, 11);

INSERT INTO usuario_enfrentamientos_ganados (usuario_id, enfrentamientos_ganados_id) VALUES
(3, 9), (4, 10);

SELECT setval('domain_entity_seq', 1000);