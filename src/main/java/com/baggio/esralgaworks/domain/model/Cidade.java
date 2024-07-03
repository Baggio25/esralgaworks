package com.baggio.esralgaworks.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import com.baggio.esralgaworks.domain.groups.Groups;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "cidade")
public class Cidade {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O campo 'nome' é obrigatório")
    @Column(nullable = false)
    private String nome;

    @Valid
    @ConvertGroup(from = Default.class, to = Groups.EstadoId.class)
    @NotNull(message = "O campo 'estado' é obrigatório")
    @ManyToOne
    @JoinColumn(name = "estado_id", nullable = false)
    private Estado estado;
}
