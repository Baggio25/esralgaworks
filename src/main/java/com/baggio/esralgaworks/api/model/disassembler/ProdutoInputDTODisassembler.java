package com.baggio.esralgaworks.api.model.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baggio.esralgaworks.api.model.dto.input.ProdutoInputDTO;
import com.baggio.esralgaworks.domain.model.Produto;

@Component
public class ProdutoInputDTODisassembler {

	@Autowired
	private ModelMapper modelMapper;

	public Produto toDomain(ProdutoInputDTO produtoInputDTO) {
		return modelMapper.map(produtoInputDTO, Produto.class);
	}
}
