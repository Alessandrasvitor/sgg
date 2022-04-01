

CREATE TABLE instituicao_ensino (
    id bigserial CONSTRAINT pk_id_instituicao_ensino PRIMARY KEY,
    dt_criacao TIMESTAMP WITH TIME ZONE NOT NULL,
    nome character varying(255) NOT NULL,
    endereco character varying(255) NOT NULL,
    avaliacao float4,
CONSTRAINT instituicao_ensino_nome_un UNIQUE (nome)
);

CREATE TABLE curso (
    id bigserial CONSTRAINT pk_id_curso PRIMARY KEY,
    dt_criacao TIMESTAMP WITH TIME ZONE  NOT NULL,
    nome character varying(255)  NOT NULL,
    descricao character varying,
    dt_inicio TIMESTAMP WITH TIME ZONE,
    dt_fim TIMESTAMP WITH TIME ZONE,
    intituicao_ensino_id bigint NOT NULL,
    usuario_id bigint NOT NULL,
    status character varying(25),
    nota float4,
    finalizado boolean,
    CONSTRAINT curso_nome_un UNIQUE (nome),
    CONSTRAINT curso_usuario_fk FOREIGN KEY (usuario_id) REFERENCES livro(id),
    CONSTRAINT curso_instituicao_fk FOREIGN KEY (intituicao_ensino_id) REFERENCES instituicao_ensino(id)
);