package com.baggio.esralgaworks.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.baggio.esralgaworks.domain.groups.Groups;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "estado")
public class Estado {

	@NotNull(groups = Groups.EstadoId.class, message = "O campo 'id' do estado é obrigatório")
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@NotBlank(message = "O campo 'nome' é obrigatório")
    @Column(nullable = false)
    private String nome;
}
