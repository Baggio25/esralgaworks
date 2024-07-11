package com.baggio.esralgaworks.api.model.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baggio.esralgaworks.api.model.dto.CidadeDTO;
import com.baggio.esralgaworks.domain.model.Cidade;

@Component
public class CidadeDTOAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public CidadeDTO toDTO(Cidade cidade) {
		return modelMapper.map(cidade, CidadeDTO.class);
	}
	
	public List<CidadeDTO> toCollectionDTO(List<Cidade> cidades){
		return cidades.stream()
				.map(cidade -> toDTO(cidade))
				.collect(Collectors.toList());
	}
	
}
