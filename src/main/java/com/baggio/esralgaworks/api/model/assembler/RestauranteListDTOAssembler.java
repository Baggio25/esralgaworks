package com.baggio.esralgaworks.api.model.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baggio.esralgaworks.api.model.dto.RestauranteListDTO;
import com.baggio.esralgaworks.domain.model.Restaurante;

@Component
public class RestauranteListDTOAssembler {
	
	@Autowired
	private ModelMapper modelMapper;

	public RestauranteListDTO toDTO(Restaurante restaurante) {
		return modelMapper.map(restaurante, RestauranteListDTO.class);
	}
	
	public List<RestauranteListDTO> toCollectionDTO(List<Restaurante> restaurantes) {
		return restaurantes.stream()
				.map(restaurante -> toDTO(restaurante))
				.collect(Collectors.toList());
	}
	
}
