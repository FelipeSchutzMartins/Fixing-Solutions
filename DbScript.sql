create database fixing_solutions;

create table cargo( id int not null auto_increment primary key,
descricao varchar(45) );

create table funcionario( id int not null auto_increment primary key,
idCargo int not null,
email varchar(70),
password char(60),
nome varchar(45),
foreign key (idCargo) references cargo(id) );

create table cliente(id int not null auto_increment primary key,
nome varchar(45),
email varchar(70),
cpf char(11) ,
telefone VARCHAR(20)
);

create table orcamento( id int not null auto_increment primary key,
data date,
valor decimal(13,2),
horasPrevistas int,
idFuncionario int not null,
idCliente int not null,
foreign key (idFuncionario) references funcionario(id),
foreign key (idCliente) references cliente(id) );

create table ordemServico(id int not null auto_increment primary key,
idOrcamento int not null,
status int,
titulo varchar(45),
dataInicio date,
dataUltimaAtualizacao date,
foreign key (idOrcamento) references orcamento(id) );

create table tipoServico(id int not null auto_increment primary key,
descricao varchar(45) );

create table tipoServico_orcamento(id int not null auto_increment primary key,
idTipoServico int not null,
idOrcamento int not null,
foreign key (idOrcamento) references orcamento(id),
foreign key (idTipoServico) references tipoServico(id) );

insert
	into
	fixing_solutions.cargo(descricao)
values ("Funcionario");

insert
	into
	fixing_solutions.cargo(descricao)
values ("Administrador");