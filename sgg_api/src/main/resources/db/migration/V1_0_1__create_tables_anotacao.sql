
create table anotacao (
     id bigserial CONSTRAINT pk_id_anotacao PRIMARY KEY,
     titulo varchar(50) not null,
     descricao text,
     data_criacao TIMESTAMP WITH TIME ZONE not null,
     data_vencimento TIMESTAMP WITH TIME ZONE,
     tipo_anotacao varchar(50) not null,
     usuario_id bigserial,
     CONSTRAINT usuario_anotacao_fk FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);
