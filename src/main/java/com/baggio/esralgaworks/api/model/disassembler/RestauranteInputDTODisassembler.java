package com.baggio.esralgaworks.api.model.disassembler;

import org.springframework.stereotype.Component;

import com.baggio.esralgaworks.api.model.dto.input.RestauranteInputDTO;
import com.baggio.esralgaworks.domain.model.Cozinha;
import com.baggio.esralgaworks.domain.model.Restaurante;

@Component
public class RestauranteInputDTODisassembler {
	
	public Restaurante toDomain(RestauranteInputDTO restauranteInputDTO) {
		Cozinha cozinha = new Cozinha();
		cozinha.setId(restauranteInputDTO.getCozinha().getId());

		Restaurante restaurante = new Restaurante();
		restaurante.setNome(restauranteInputDTO.getNome());
		restaurante.setTaxaFrete(restauranteInputDTO.getTaxaFrete());
		restaurante.setCozinha(cozinha);

		return restaurante;
	}
}
