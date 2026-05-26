INSERT INTO usuarios (nome, email, senha, perfil)
VALUES ('Dr. Joao Carlos', 'drjoaocarlos@hospital.com', '123456', 'MEDICO');

INSERT INTO usuarios (nome, email, senha, perfil)
VALUES ('Enf. Maria Fernanda', 'enfmariafernanda@hospital.com', '123456', 'ENFERMEIRO');

INSERT INTO usuarios (nome, email, senha, perfil)
VALUES ('Marcus Vinicius Paciente', 'marcusvinicius@hospital.com', '123456', 'PACIENTE');

INSERT INTO consultas (data_hora, especialidade, observacoes, status, medico_id, enfermeiro_id, paciente_id)
VALUES ('2026-05-20T09:00:00', 'Cardiologia', 'Consulta inicial do paciente', 'AGENDADA', 1, 2, 3);

INSERT INTO consultas (data_hora, especialidade, observacoes, status, medico_id, enfermeiro_id, paciente_id)
VALUES ('2026-05-22T14:30:00', 'Clínica Geral', 'Retorno para avaliação', 'AGENDADA', 1, 2, 3);