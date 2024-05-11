CREATE TABLE permissao (
	id bigint not null auto_increment,
    nome varchar(80) not null,
	descricao varchar(200) not null,
	
    constraint pk_id_permissao primary key (id)
)engine=InnoDB default charset=utf8;

CREATE TABLE grupo (
	id bigint not null auto_increment,
    nome varchar(80) not null,
		
    constraint pk_id_grupo primary key (id)
)engine=InnoDB default charset=utf8;

CREATE TABLE usuario (
	id bigint not null auto_increment,
    nome varchar(80) not null,
	email varchar(100) not null,
    senha varchar(80) not null,
    data_cadastro datetime not null,
    
    constraint pk_id_usuario primary key (id)
)engine=InnoDB default charset=utf8;

CREATE TABLE grupo_permissao (
	grupo_id bigint not null,
	permissao_id bigint not null,
    
    constraint pk_grupo_permissao primary key (grupo_id, permissao_id),
    constraint fk_grupo_permissao_grupo foreign key (grupo_id) references grupo (id),
    constraint fk_grupo_permissao_permissao foreign key (permissao_id) references permissao (id)
)engine=InnoDB default charset=utf8;

CREATE TABLE usuario_grupo (
	usuario_id bigint not null,
	grupo_id bigint not null,
    
    constraint pk_usuario_grupo primary key (usuario_id, grupo_id),
    constraint fk_usuario_grupo_usuario foreign key (usuario_id) references usuario (id),
    constraint fk_usuario_grupo_grupo foreign key (grupo_id) references grupo (id)
)engine=InnoDB default charset=utf8;
