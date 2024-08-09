package com.baggio.esralgaworks.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

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

    @Embedded
    private Endereco endereco;
    
    @CreationTimestamp
    @Column(name = "data_cadastro", columnDefinition = "datetime", nullable = false)
    private OffsetDateTime dataCadastro;

    @UpdateTimestamp
    @Column(name = "data_atualizacao", columnDefinition = "datetime", nullable = false)
    private OffsetDateTime dataAtualizacao;
    
    private Boolean ativo = Boolean.TRUE;
    
    @ManyToMany
    @JoinTable(name = "restaurante_forma_pagamento",
    		joinColumns = @JoinColumn(name = "restaurante_id"),
    		inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id")
    )
    private List<FormaPagamento> formasPagamento = new ArrayList<>();
    
    @JsonIgnore
    @OneToMany(mappedBy = "restaurante")
    private List<Produto> produtos = new ArrayList<>();
    
    public void ativar() {
    	setAtivo(true);
    }
    
    public void inativar() {
    	setAtivo(false);
    }
}
