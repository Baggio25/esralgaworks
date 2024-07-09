package com.baggio.esralgaworks.api.model.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.baggio.esralgaworks.api.model.dto.CozinhaDTO;
import com.baggio.esralgaworks.api.model.dto.RestauranteDTO;
import com.baggio.esralgaworks.domain.model.Restaurante;

@Component
public class RestauranteDTOAssembler {

	public RestauranteDTO toDTO(Restaurante restaurante) {
		CozinhaDTO cozinhaDTO = new CozinhaDTO();
		cozinhaDTO.setId(restaurante.getCozinha().getId());
		cozinhaDTO.setNome(restaurante.getCozinha().getNome());
		
		RestauranteDTO restauranteDTO = new RestauranteDTO();
		restauranteDTO.setId(restaurante.getId());
		restauranteDTO.setNome(restaurante.getNome());
		restauranteDTO.setTaxaFrete(restaurante.getTaxaFrete());
		restauranteDTO.setCozinhaDTO(cozinhaDTO);
		return restauranteDTO;
	}
	
	public List<RestauranteDTO> toCollectionDTO(List<Restaurante> restaurantes) {
		return restaurantes.stream()
				.map(restaurante -> toDTO(restaurante))
				.collect(Collectors.toList());
	}
	
	
	
}
