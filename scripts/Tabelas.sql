

create table Proprietario 
(
id int not null auto_increment,
nome varchar(150) not null,
telefone varchar(15),
telefone1 varchar(15),
telefone2 varchar(15),
email varchar(50),
num_conta varchar(20) ,
agencia varchar(20),
instituicao varchar(50) ,
operacao varchar(3) ,
endereco varchar(200),
ativo boolean,
data_deposito varchar(2),
PRIMARY KEY (Id)
);

create table Inquilino
(
id int not null auto_increment,
nome varchar(150) not null,
telefone varchar(15),
telefone1 varchar(15),
telefone2 varchar(15),
email varchar(50),
cpf varchar(15),
rg varchar(15),
data_boleto int,
mes_contrato int,
ativo boolean,
PRIMARY KEY (Id)
);

create table Apartamento
(
id int not null auto_increment,
edificio varchar(150),
numero varchar(50) not null,
contrato varchar(10),
id_inquilino int,
id_proprietario int,
aluguel float,
PRIMARY KEY (Id),
FOREIGN KEY (id_proprietario) REFERENCES Proprietario(id),
FOREIGN KEY (id_inquilino) REFERENCES Inquilino(id)
);

create table Pagamento
(
id int not null auto_increment,
mes date ,
id_apartemento int not null,
data_pagamento date,
nome_comprovante_pagamento varchar(150),
data_deposito date,
nome_comprovante_deposito varchar(150),
valor_deposito float,
PRIMARY KEY (Id),
FOREIGN KEY (id_apartemento) REFERENCES Apartamento(id)
);

--------------------Updates------------------------------------

ALTER TABLE Proprietario
add data_deposito varchar(2);

ALTER TABLE Proprietario
add telefone1 varchar(15);
ALTER TABLE Proprietario
add telefone2 varchar(15);

ALTER TABLE Inquilino
add telefone1 varchar(15);
ALTER TABLE Inquilino
add telefone2 varchar(15);

--------------------Controle acesso----------------------------

create table usuario 
(
	id int not null auto_increment,
	nome varchar(150) not null,
	login varchar(100) unique not null,
	email varchar(150) ,
	senha varchar(50) unique not null,
	PRIMARY KEY (id)
);
