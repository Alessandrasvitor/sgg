
create table livro (
       id int(4) AUTO_INCREMENT PRIMARY KEY,
       nome varchar(30) not null,
       sub_nome varchar(100),
       data_criacao DATETIME not null,
       genero varchar(50) not null,
       usuario_id int(4) not null,
       CONSTRAINT fk_usuario_livro FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

create table capitulo (
      id int(4) AUTO_INCREMENT PRIMARY KEY,
      titulo varchar(50) not null,
      capitulo longtext,
      ordem int(2) not null,
      data_criacao DATETIME not null,
      livro_id int(4) not null,
      CONSTRAINT fk_capitulo_livro FOREIGN KEY (livro_id) REFERENCES livro(id)
);