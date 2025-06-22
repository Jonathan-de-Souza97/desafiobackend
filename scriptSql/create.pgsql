drop schema if exists desafiobackend cascade;

create schema desafiobackend;

create table desafiobackend.vendedor (
	id uuid,
	matricula text,
	nome text,
	dataDeNascimento timestamp,
	documento text,
	email text,
	tipoDeContratacao integer,
	numeroFilial integer,
	primary key (id)
);

create table desafiobackend.controleSequenciamentoMatricula (
	lastNumber integer
);

insert into desafiobackend.controleSequenciamentoMatricula
	(lastNumber)
	VALUES
	(0);

