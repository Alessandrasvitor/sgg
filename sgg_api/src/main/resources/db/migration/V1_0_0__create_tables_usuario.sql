
create table usuario (
     id bigserial CONSTRAINT pk_id_usuario PRIMARY KEY,
     nome varchar(50) not null,
     email varchar(100) not null,
     senha varchar(255) not null,
     perfil varchar(50) not null,
     CONSTRAINT equipe_email_un UNIQUE (email)
);

INSERT INTO usuario (nome, email, senha, perfil) VALUES
    ('Super Admin', 'sansyro@email.com', '6a0344b2109ac6fa02f791acdc007330198cecf89f20919fefa12aa949f08258', 'SUPER_ADMIN'),
    ('Visitante', 'email@email.com', '6a0344b2109ac6fa02f791acdc007330198cecf89f20919fefa12aa949f08258', 'VISITANTE');

CREATE SEQUENCE hibernate_sequence
    START WITH 5
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;