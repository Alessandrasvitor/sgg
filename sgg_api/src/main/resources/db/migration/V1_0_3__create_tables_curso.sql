
CREATE TABLE instituicao_ensino (
    id int(4) AUTO_INCREMENT PRIMARY KEY,
    dt_criacao DATETIME NOT NULL,
    nome varchar(50) NOT NULL UNIQUE,
    endereco varchar(255) NOT NULL,
    avaliacao float(2)
);

CREATE TABLE curso (
   id int(4) AUTO_INCREMENT PRIMARY KEY,
   dt_criacao DATETIME  NOT NULL,
   nome varchar(100)  NOT NULL UNIQUE,
   descricao longtext,
   dt_inicio DATETIME,
   dt_fim DATETIME,
   intituicao_ensino_id int(4) NOT NULL,
   usuario_id int(4) NOT NULL,
   status varchar(25),
   nota float(2),
   finalizado boolean,
   CONSTRAINT fk_curso_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id),
   CONSTRAINT fk_curso_instituicao FOREIGN KEY (intituicao_ensino_id) REFERENCES instituicao_ensino(id)
);