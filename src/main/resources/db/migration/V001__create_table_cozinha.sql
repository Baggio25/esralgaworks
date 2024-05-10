create table cozinha(
	id bigint not null auto_increment, 
    nome varchar(60) not null,
    
    constraint pk_id_cozinha primary key (id)
)engine=InnoDB default charset=utf8;