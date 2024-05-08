package com.baggio.esralgaworks.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table( name = "restaurante")
public class Restaurante {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(name = "taxa_frete", nullable = false)
    private BigDecimal taxaFrete;

    @ManyToOne
    @JoinColumn(name = "cozinha_id", nullable = false)
    private Cozinha cozinha;

    @JsonIgnore
    @Embedded
    private Endereco endereco;
    
    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "restaurante_forma_pagamento",
    		joinColumns = @JoinColumn(name = "restaurante_id"),
    		inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id")
    )
    private List<FormaPagamento> formasPagamento = new ArrayList<>();
    
    
}
