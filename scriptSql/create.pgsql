drop schema if exists desafiobackend cascade;
drop schema if exists desafiobackendtest cascade;

create schema desafiobackend;
create schema desafiobackendtest;

create table desafiobackend.vendedor (
	id uuid,
	matricula text,
	nome text,
	dataDeNascimento DATE,
	documento text,
	email text,
	tipoDeContratacao text,
	numeroFilial integer,
	primary key (id)
);

create table desafiobackendtest.vendedor (
	id uuid,
	matricula text,
	nome text,
	dataDeNascimento DATE,
	documento text,
	email text,
	tipoDeContratacao text,
	numeroFilial integer,
	primary key (id)
);

create table desafiobackend.controlesequenciamentomatricula (
	ultimoCodigo integer
);

create table desafiobackendtest.controlesequenciamentomatricula (
	ultimoCodigo integer
);

insert into desafiobackend.controlesequenciamentomatricula
	(ultimoCodigo)
	VALUES
	(0);

insert into desafiobackendtest.controlesequenciamentomatricula
	(ultimoCodigo)
	VALUES
	(0);

