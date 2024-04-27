package com.baggio.esralgaworks.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baggio.esralgaworks.domain.model.Restaurante;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
}
