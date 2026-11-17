-- 1º passo: pacientes e usuarios nao dependem de nenhuma outra tabela.
CREATE TABLE pacientes (
                           id              BIGINT       NOT NULL AUTO_INCREMENT,
                           nome            VARCHAR(100) NOT NULL,
                           cpf             VARCHAR(11)  NOT NULL,
                           telefone        VARCHAR(20)  NOT NULL,
                           data_nascimento DATE         NOT NULL,
                           ativo           BOOLEAN      NOT NULL DEFAULT TRUE,
                           PRIMARY KEY (id),
                           CONSTRAINT uk_pacientes_cpf UNIQUE (cpf)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE usuarios (
                          id      BIGINT       NOT NULL AUTO_INCREMENT,
                          nome    VARCHAR(100) NOT NULL,
                          cpf     VARCHAR(11)  NOT NULL,
                          email   VARCHAR(150) NOT NULL,
                          senha   VARCHAR(100) NOT NULL,
                          perfil  VARCHAR(20)  NOT NULL,
                          cro     VARCHAR(20)  NULL,
                          turno   VARCHAR(20)  NULL,
                          ativo   BOOLEAN      NOT NULL DEFAULT TRUE,
                          PRIMARY KEY (id),
                          CONSTRAINT uk_usuarios_cpf   UNIQUE (cpf),
                          CONSTRAINT uk_usuarios_email UNIQUE (email),
                          CONSTRAINT ck_usuarios_perfil CHECK (perfil IN ('ADMIN', 'DENTISTA', 'RECEPCIONISTA')),
                          CONSTRAINT ck_usuarios_cro    CHECK (perfil <> 'DENTISTA' OR cro IS NOT NULL)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 2º passo: consultas depende de pacientes e usuarios.
CREATE TABLE consultas (
                           id               BIGINT      NOT NULL AUTO_INCREMENT,
                           paciente_id      BIGINT      NOT NULL,
                           dentista_id      BIGINT      NOT NULL,
                           data_hora_inicio DATETIME    NOT NULL,
                           data_hora_fim    DATETIME    NOT NULL,
                           status           VARCHAR(20) NOT NULL DEFAULT 'AGENDADA',
                           PRIMARY KEY (id),
                           CONSTRAINT fk_consultas_paciente FOREIGN KEY (paciente_id) REFERENCES pacientes (id),
                           CONSTRAINT fk_consultas_dentista FOREIGN KEY (dentista_id) REFERENCES usuarios (id),
                           CONSTRAINT ck_consultas_status    CHECK (status IN ('AGENDADA', 'REALIZADA', 'CANCELADA')),
                           CONSTRAINT ck_consultas_intervalo CHECK (data_hora_inicio < data_hora_fim),
                           INDEX idx_consultas_dentista_inicio (dentista_id, data_hora_inicio)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 3º passo: prontuarios depende de consultas (relacao 1:1 garantida pela UNIQUE).
CREATE TABLE prontuarios (
                             id          BIGINT       NOT NULL AUTO_INCREMENT,
                             consulta_id BIGINT       NOT NULL,
                             descricao   VARCHAR(500) NOT NULL,
                             PRIMARY KEY (id),
                             CONSTRAINT uk_prontuarios_consulta UNIQUE (consulta_id),
                             CONSTRAINT fk_prontuarios_consulta FOREIGN KEY (consulta_id) REFERENCES consultas (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

