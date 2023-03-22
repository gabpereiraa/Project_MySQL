/**
* Radical Motos
* @author Gabriel Anastacio
*/

create database dbradicalmotos;

show databases;

use dbradicalmotos;

create table usuarios ( /* Tabela com campos*/
	iduser int primary key auto_increment,
	nome varchar(30) not null, /*nome com 30 caracter e obrigatorio*/
	login varchar(20) not null unique, /*unique = nao aceita valores duplicados*/
	senha varchar(250) not null
);

describe usuarios;

select * from usuarios; /*Ver tabela do usuario*/

insert into usuarios(nome,login,senha) values ('Administrador','admin','admin'); /*Inserindo um usuario na tabela*/

-- md5() gera um hash (criptografia)
insert into usuarios(nome,login,senha) values ('Paloma Oliveira','paloma',md5('12345')); /*Criando usuario com criptografia de senha*/
insert into usuarios(nome,login,senha) values (	'Kauan Oliveira','k',md5('12345'));

-- gerando um erro com unique
insert into usuarios(nome,login,senha) values ('Paloma Silva','paloma',md5(12345)); 