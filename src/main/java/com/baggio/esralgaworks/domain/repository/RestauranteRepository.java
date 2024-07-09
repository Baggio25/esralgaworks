package com.baggio.esralgaworks.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.baggio.esralgaworks.domain.model.Restaurante;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
	
	@Query("from Restaurante r left join fetch r.cozinha")
	List<Restaurante> findAll();

}
