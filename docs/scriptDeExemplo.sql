-- =========================
-- USUARIOS (1 admin + 9 medicos)
-- =========================

INSERT INTO usuario (id_usuario, email, password, papeis, admin_date) VALUES
(1, 'admin@aegis.com', '$2a$10$hashadmin', 'ROLE_ADMIN', '2027-01-01'),

(2, 'medico1@aegis.com', '$2a$10$hash', 'ROLE_MEDICO', NULL),
(3, 'medico2@aegis.com', '$2a$10$hash', 'ROLE_MEDICO', NULL),
(4, 'medico3@aegis.com', '$2a$10$hash', 'ROLE_MEDICO', NULL),
(5, 'medico4@aegis.com', '$2a$10$hash', 'ROLE_MEDICO', NULL),
(6, 'medico5@aegis.com', '$2a$10$hash', 'ROLE_MEDICO', NULL),
(7, 'medico6@aegis.com', '$2a$10$hash', 'ROLE_MEDICO', NULL),
(8, 'medico7@aegis.com', '$2a$10$hash', 'ROLE_MEDICO', NULL),
(9, 'medico8@aegis.com', '$2a$10$hash', 'ROLE_MEDICO', NULL),
(10, 'medico9@aegis.com', '$2a$10$hash', 'ROLE_MEDICO', NULL);

-- =========================
-- MEDICOS
-- =========================

INSERT INTO medico (id_medico, nome, sexo, idade, usuario_id) VALUES
(1, 'Dr. Carlos Silva', 'M', 45, 2),
(2, 'Dra. Ana Souza', 'F', 38, 3),
(3, 'Dr. João Lima', 'M', 50, 4),
(4, 'Dra. Paula Mendes', 'F', 41, 5),
(5, 'Dr. Ricardo Alves', 'M', 36, 6),
(6, 'Dra. Fernanda Costa', 'F', 29, 7),
(7, 'Dr. Bruno Rocha', 'M', 55, 8),
(8, 'Dra. Juliana Ferreira', 'F', 33, 9),
(9, 'Dr. Marcos Pereira', 'M', 47, 10);

-- =========================
-- PACIENTES
-- =========================

INSERT INTO paciente (id_paciente, nome_paciente, cpf_paciente, data_nascimento) VALUES
(1, 'Lucas Martins', '11111111111', '1995-04-12'),
(2, 'Mariana Oliveira', '22222222222', '1988-07-23'),
(3, 'Pedro Henrique', '33333333333', '2000-01-10'),
(4, 'Julia Santos', '44444444444', '1992-09-05'),
(5, 'Rafael Gomes', '55555555555', '1985-03-18'),
(6, 'Camila Rocha', '66666666666', '1999-11-30'),
(7, 'Gabriel Alves', '77777777777', '1990-06-14'),
(8, 'Aline Costa', '88888888888', '1993-02-02'),
(9, 'Bruno Almeida', '99999999999', '1987-12-25'),
(10, 'Fernanda Lima', '10101010101', '1996-08-08');

-- =========================
-- DOENCAS
-- =========================

INSERT INTO doenca (id_doenca, nome_doenca, descricao_doenca, hereditaria) VALUES
(1, 'Diabetes Tipo 2', 'Resistência à insulina', false),
(2, 'Hipertensão', 'Pressão alta crônica', false),
(3, 'Asma', 'Inflamação das vias aéreas', true),
(4, 'Gripe', 'Infecção viral respiratória', false),
(5, 'COVID-19', 'Infecção por coronavírus', false),
(6, 'Anemia', 'Baixa hemoglobina', true),
(7, 'Enxaqueca', 'Dor de cabeça intensa', true),
(8, 'Gastrite', 'Inflamação do estômago', false),
(9, 'Depressão', 'Transtorno mental', true),
(10, 'Colesterol Alto', 'Dislipidemia', false);

-- =========================
-- SINTOMAS
-- =========================

INSERT INTO sintoma (id_sintoma, nome_sintoma, descricao_sintoma) VALUES
(1, 'Febre', 'Temperatura elevada'),
(2, 'Tosse', 'Tosse seca ou produtiva'),
(3, 'Dor de cabeça', 'Cefaleia'),
(4, 'Fadiga', 'Cansaço extremo'),
(5, 'Falta de ar', 'Dificuldade respiratória'),
(6, 'Náusea', 'Enjoo'),
(7, 'Tontura', 'Sensação de desequilíbrio'),
(8, 'Dor abdominal', 'Dor no estômago'),
(9, 'Ansiedade', 'Estado de preocupação'),
(10, 'Perda de apetite', 'Redução de fome');

-- =========================
-- DOENCA_SINTOMA
-- =========================

INSERT INTO doenca_sintoma (id_doenca, id_sintoma) VALUES
(1, 10),
(1, 7),
(2, 7),
(3, 5),
(4, 1),
(4, 2),
(5, 1),
(5, 2),
(5, 5),
(6, 4),
(7, 3),
(8, 8),
(9, 9),
(10, 6);

-- =========================
-- CONSULTAS (20)
-- =========================

INSERT INTO consulta (id_consulta, medico_id, paciente_id, data_consulta, descricao) VALUES
(1, 1, 1, '2026-06-01 10:00:00', 'Consulta geral'),
(2, 2, 2, '2026-06-02 11:00:00', 'Avaliação inicial'),
(3, 3, 3, '2026-06-03 09:30:00', 'Check-up'),
(4, 4, 4, '2026-06-04 14:00:00', 'Sintomas respiratórios'),
(5, 5, 5, '2026-06-05 16:00:00', 'Dor abdominal'),
(6, 6, 6, '2026-06-06 10:30:00', 'Fadiga constante'),
(7, 7, 7, '2026-06-07 13:00:00', 'Dor de cabeça'),
(8, 8, 8, '2026-06-08 15:00:00', 'Ansiedade'),
(9, 9, 9, '2026-06-09 08:00:00', 'Exame de rotina'),
(10, 1, 10, '2026-06-10 17:00:00', 'Retorno'),

(11, 2, 1, '2026-06-11 10:00:00', 'Acompanhamento'),
(12, 3, 2, '2026-06-12 11:00:00', 'Resultados'),
(13, 4, 3, '2026-06-13 09:00:00', 'Sintomas gripais'),
(14, 5, 4, '2026-06-14 14:00:00', 'Avaliação COVID'),
(15, 6, 5, '2026-06-15 16:00:00', 'Dor persistente'),
(16, 7, 6, '2026-06-16 10:00:00', 'Consulta neurológica'),
(17, 8, 7, '2026-06-17 13:30:00', 'Estresse'),
(18, 9, 8, '2026-06-18 15:30:00', 'Reavaliação'),
(19, 1, 9, '2026-06-19 08:30:00', 'Exames'),
(20, 2, 10, '2026-06-20 17:30:00', 'Alta médica');

-- =========================
-- CONSULTA_SINTOMA
-- =========================

INSERT INTO consulta_sintoma (consulta_id, sintoma_id) VALUES
(1,1),(1,3),
(2,2),(2,4),
(3,7),
(4,2),(4,5),
(5,8),(5,6),
(6,4),
(7,3),
(8,9),
(9,1),(9,4),
(10,2),

(11,7),
(12,1),
(13,2),(13,1),
(14,1),(14,5),
(15,8),
(16,3),
(17,9),
(18,4),
(19,7),
(20,2);

-- =========================
-- CONSULTA_DOENCA
-- =========================

INSERT INTO consulta_doenca (consulta_id, doenca_id) VALUES
(1,2),
(2,3),
(3,1),
(4,5),
(5,8),
(6,6),
(7,7),
(8,9),
(9,4),
(10,2),

(11,2),
(12,1),
(13,4),
(14,5),
(15,8),
(16,7),
(17,9),
(18,6),
(19,10),
(20,2);