package com.baggio.esralgaworks.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.baggio.esralgaworks.domain.model.Restaurante;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
	//Left join fetch = Para poder retornar o restaurante caso não tenha nenhuma forma de pagamento vinculada, 
	//se for só join fetch não retornaria nada
	@Query("from Restaurante r left join fetch r.cozinha left join fetch r.formasPagamento")
	List<Restaurante> findAll();

}
