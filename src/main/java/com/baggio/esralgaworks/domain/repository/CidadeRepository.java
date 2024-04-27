package com.baggio.esralgaworks.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baggio.esralgaworks.domain.model.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {
}
