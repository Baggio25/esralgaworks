package com.baggio.esralgaworks.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baggio.esralgaworks.domain.model.Cozinha;

public interface CozinhaRepository extends JpaRepository<Cozinha, Long> {

	Optional<Cozinha> findByNome(String nome);

}
