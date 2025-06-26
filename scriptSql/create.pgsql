drop schema if exists desafiobackend cascade;
drop schema if exists desafiobackendtest cascade;

create schema desafiobackend;
create schema desafiobackendtest;

create table desafiobackend.vendedor (
	id uuid NOT NULL,
	matricula text NOT NULL,
	nome text NOT NULL,
	dataDeNascimento date,
	documento text NOT NULL,
	email text NOT NULL,
	tipoDeContratacao text NOT NULL,
	numeroFilial integer NOT NULL,
	primary key (id)
);

CREATE INDEX idx_matricula ON desafiobackend.vendedor (matricula);
CREATE INDEX idx_documento_tipoContratacao ON desafiobackend.vendedor (matricula, tipoDeContratacao);

create table desafiobackendtest.vendedor (
	id uuid NOT NULL,
	matricula text NOT NULL,
	nome text NOT NULL,
	dataDeNascimento date,
	documento text NOT NULL,
	email text NOT NULL,
	tipoDeContratacao text NOT NULL,
	numeroFilial integer NOT NULL,
	primary key (id)
);

CREATE INDEX idx_matricula ON desafiobackendtest.vendedor (matricula);
CREATE INDEX idx_documento_tipoContratacao ON desafiobackendtest.vendedor (matricula, tipoDeContratacao);

create table desafiobackend.controlesequenciamentomatricula (
	ultimoCodigo integer,
	primary key (ultimoCodigo)
);

create table desafiobackendtest.controlesequenciamentomatricula (
	ultimoCodigo integer,
	primary key (ultimoCodigo)
);

insert into desafiobackend.controlesequenciamentomatricula
	(ultimoCodigo)
	VALUES
	(0);

insert into desafiobackendtest.controlesequenciamentomatricula
	(ultimoCodigo)
	VALUES
	(0);

