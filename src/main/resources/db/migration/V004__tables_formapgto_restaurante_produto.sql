CREATE TABLE forma_pagamento (
	id bigint not null auto_increment,
	descricao varchar(80) not null,
	
    constraint pk_id_forma_pagamento primary key (id)
)engine=InnoDB default charset=utf8;

CREATE TABLE restaurante (
	id bigint not null auto_increment,	
	nome varchar(80) not null,
	taxa_frete decimal(10,2) not null,
	data_atualizacao datetime not null,
	data_cadastro datetime not null,
	endereco_cep varchar(9),
	endereco_logradouro varchar(100),
	endereco_numero varchar(20),
	endereco_complemento varchar(60),
	endereco_bairro varchar(60),
    endereco_cidade_id bigint,
    cozinha_id bigint not null,    
	
    constraint pk_id_restaurante primary key (id),
    constraint fk_cidade_restaurante foreign key (endereco_cidade_id) references cidade (id),
    constraint fk_cozinha_restaurante foreign key (cozinha_id) references cozinha (id)
)engine=InnoDB default charset=utf8;

CREATE TABLE restaurante_forma_pagamento (
	restaurante_id bigint not null,
	forma_pagamento_id bigint not null,
    
    constraint pk_restaurante_forma_pagamento primary key (restaurante_id, forma_pagamento_id),
    constraint fk_restaurante_forma_pagamento_restaurante foreign key (restaurante_id) references restaurante (id),
    constraint fk_restaurante_forma_pagamento_forma_pagamento foreign key (forma_pagamento_id) references forma_pagamento (id)
)engine=InnoDB default charset=utf8;

CREATE TABLE produto (
	id bigint not null auto_increment,
	nome varchar(80) not null,
    descricao varchar(200) not null,
    preco decimal(10,2) not null,
	ativo tinyint(1) not null,
    restaurante_id bigint not null,
    
    constraint pk_id_produto primary key (id),
    constraint fk_produto_restaurante foreign key (restaurante_id) references restaurante (id)
)engine=InnoDB default charset=utf8;
