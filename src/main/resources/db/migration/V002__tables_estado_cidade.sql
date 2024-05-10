CREATE TABLE estado (
	id bigint not null auto_increment,
    nome varchar(80) not null,

	constraint pk_id_estado primary key (id)
)engine=InnoDB default charset=utf8;

CREATE TABLE cidade (
	id bigint not null auto_increment,
    nome varchar(80) not null,
    estado_id bigint not null,
    
    constraint pk_id_cidade primary key (id),
    constraint fk_estado_cidade foreign key (estado_id) references estado (id)
)engine=InnoDB default charset=utf8;