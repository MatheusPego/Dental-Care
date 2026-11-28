-- Acesso único: usuarios sem perfil, CRO, turno e CPF.
ALTER TABLE usuarios DROP CHECK ck_usuarios_cro;
ALTER TABLE usuarios DROP CHECK ck_usuarios_perfil;
ALTER TABLE usuarios DROP INDEX uk_usuarios_cpf;
ALTER TABLE usuarios DROP COLUMN perfil, DROP COLUMN cro, DROP COLUMN turno, DROP COLUMN cpf;

-- Agenda única: o profissional é o usuário logado.
ALTER TABLE consultas DROP FOREIGN KEY fk_consultas_dentista;
ALTER TABLE consultas DROP INDEX idx_consultas_dentista_inicio;
ALTER TABLE consultas RENAME COLUMN dentista_id TO profissional_id;
ALTER TABLE consultas ADD INDEX idx_consultas_profissional_inicio (profissional_id, data_hora_inicio);
ALTER TABLE consultas ADD CONSTRAINT fk_consultas_profissional FOREIGN KEY (profissional_id) REFERENCES usuarios (id);

-- Prontuário com mais espaço.
ALTER TABLE prontuarios MODIFY descricao VARCHAR(2000) NOT NULL;