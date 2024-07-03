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

	@NotNull(groups = Groups.EstadoId.class)
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@NotBlank
    @Column(nullable = false)
    private String nome;
}
