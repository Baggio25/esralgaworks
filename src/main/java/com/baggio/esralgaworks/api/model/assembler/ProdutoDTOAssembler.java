package com.baggio.esralgaworks.api.model.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baggio.esralgaworks.api.model.dto.ProdutoDTO;
import com.baggio.esralgaworks.domain.model.Produto;

@Component
public class ProdutoDTOAssembler {
	
	@Autowired
	private ModelMapper modelMapper;

	public ProdutoDTO toDTO(Produto produto) {
		return modelMapper.map(produto, ProdutoDTO.class);
	}
	
	public List<ProdutoDTO> toCollectionDTO(List<Produto> produtos) {
		return produtos.stream()
				.map(produto -> toDTO(produto))
				.collect(Collectors.toList());
	}
	
}
