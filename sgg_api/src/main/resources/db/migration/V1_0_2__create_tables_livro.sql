
create table livro (
     id bigserial CONSTRAINT pk_id_livro PRIMARY KEY,
     nome varchar(30) not null,
     sub_nome varchar(100),
     data_criacao TIMESTAMP WITH TIME ZONE not null,
     genero varchar(50) not null,
     usuario_id bigserial not null,
     CONSTRAINT usuario_livro_fk FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

create table capitulo (
    id bigserial CONSTRAINT pk_id_capitulo PRIMARY KEY,
    titulo varchar(50) not null,
    capitulo text,
    ordem integer not null,
    data_criacao TIMESTAMP WITH TIME ZONE not null,
    livro_id bigserial not null,
    CONSTRAINT capitulo_livro_fk FOREIGN KEY (livro_id) REFERENCES livro(id)
);