/**
 Agenda de contatos
 @author Professor José de Assis
*/

/*** banco de dados ***/
-- exibir os bancos de dados do servidor
show databases;
-- criar um novo banco de dados
create database dbagenda;
-- selecionar o banco de dados
use dbagenda;
-- excluir um banco de dados
drop database dbagenda;

/*** tabela ***/

-- create table (cria uma tabela)
-- int tipo de dados (número inteiro)
-- primary key (PK) "chave primária"
-- auto_increment (numeração automática)
-- varchar(30) tipo de dados String (máximo de caracteres)
-- not null (validação campo obrigatório)
create table contatos ( 
	id int primary key auto_increment,
    nome varchar(30) not null,
    fone varchar(15) not null,
	email varchar(30)
);

-- para verificar as tabelas existentes no banco de dados
show tables;

-- para descrever a tabela usamos o comando describe
describe contatos;

-- alterando o nome de um campo da tabela
alter table contatos change nome contato varchar(30) not null;
alter table contatos change fone telefone varchar(15) not null;

-- adicionando um campo a tabela
alter table contatos add column obs varchar(250);

-- adicionando um campo a tabela em um local específico
alter table contatos add column fone2 varchar(15) after telefone;

-- modificando um atributo (tipo de dados, validação etc)
alter table contatos modify column fone2 varchar(15) not null;
alter table contatos modify column contato varchar(50) not null;

-- apagar um campo da tabela
alter table contatos drop column obs;

-- apagar a tabela inteira
drop table contatos;

/**************** CRUD *****************/
-- CRUD [Create]
insert into contatos (nome,fone,email)
values ('José de Assis','99999-1234','jose@email.com');

insert into contatos (nome,fone)
values ('Bill Gates','99999-2222');

insert into contatos (nome,fone,email)
values ('Bruce Wayne','99999-3333','batman@email.com');

-- exemplo de erro (campo obrigatório)
insert into contatos (nome,email)
values ('Linus torvalds','linus@email.com');

-- exemplo de erro (limite de caracteres)
insert into contatos(nome,fone,email)
values ('Leandro Ramos','(55)(11)99999-8888','leando2email.com');

-- CRUD [Read]

-- selecionar tudo no banco de dados (CUIDADO!)
select * from contatos;

-- selecionar de acordo com um criterio
-- pesquisando por ID
select * from contatos where id = 1;
-- pesquisando por nome
select * from contatos where nome = 'Bill Gates';
-- pesquisando por letra
select * from contatos where nome like 'b%';

-- seleção personalizada
-- filtrar por nome e telefone
select nome, fone from contatos;
-- filtrar nome para contato, fone para telefone e mail para e-mail
select nome as Contato, fone as Telefone, email as E_mail from contatos;
-- filtrar nome para contato, fone para telefone e mail para e-mail em ordem alfabetica
select nome as Contato, fone as Telefone, email as E_mail from contatos order by nome;
-- filtrar nome para contato, fone para telefone e mail para e-mail em ordem Z ate A
select nome as Contato, fone as Telefone, email as E_mail from contatos order by nome desc;

-- CRUID [update] CUIDADO !!!!! sempre usar um criterio (ID como criterio)
-- nao pode usar como criterio nome ou sem criterio
-- EXEMPLO DE ERRO
update contatos set fone='91111-1111' where nome = 'Sirlene Sanches';
update contatos set fone='00000-0000';

-- updates corretos
-- alterando telefone do contato
update contatos set fone = '91111-1111' where id = '6'; 
-- alterando email do contato
update contatos set email = 'julia@email.com' where id = '5';
-- alterando todos os campos do usuario
update contatos set nome ='Solange Silva', fone ='92323-2323', email='solange@email.com' where id ='7';

-- CRUID [Delete] CUIDADO !!!!! sempre usar um criterio (ID como criterio)
delete from contatos where nome ='Sirlene Sanches';
delete from contatos;

-- delete correto
delete from contatos where id= '6';