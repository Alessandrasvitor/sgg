
create table usuario (
     id int(4) AUTO_INCREMENT PRIMARY KEY,
     nome varchar(50) not null,
     email varchar(100) not null UNIQUE,
     senha varchar(255) not null,
     perfil varchar(50) not null
);

INSERT INTO usuario (nome, email, senha, perfil) VALUES
     ('Super Admin', 'sansyro@email.com', '55f190954d1e6cf2d16d931bb37abeac2505f79106a9c02e44bbcafeedd6c384', 'SUPER_ADMIN'),
     ('Visitante', 'email@email.com', '55f190954d1e6cf2d16d931bb37abeac2505f79106a9c02e44bbcafeedd6c384', 'VISITANTE');

