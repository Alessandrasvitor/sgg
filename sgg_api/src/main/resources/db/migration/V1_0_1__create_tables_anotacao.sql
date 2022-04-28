
create table anotacao (
      id int(4) AUTO_INCREMENT PRIMARY KEY,
      titulo varchar(50) not null,
      descricao longtext,
      data_criacao DATETIME not null,
      data_vencimento DATETIME,
      tipo_anotacao varchar(50) not null,
      usuario_id int(4),
      CONSTRAINT fk_anotacao_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);
