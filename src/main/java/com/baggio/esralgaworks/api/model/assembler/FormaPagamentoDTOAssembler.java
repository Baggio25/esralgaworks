package com.baggio.esralgaworks.api.model.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baggio.esralgaworks.api.model.dto.FormaPagamentoDTO;
import com.baggio.esralgaworks.domain.model.FormaPagamento;

@Component
public class FormaPagamentoDTOAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public FormaPagamentoDTO toDTO(FormaPagamento formaPagamento) {
		return modelMapper.map(formaPagamento, FormaPagamentoDTO.class);
	}
	
	public List<FormaPagamentoDTO> toCollectionDTO(List<FormaPagamento> formasPagamento){
		return formasPagamento.stream()
				.map(formaPagamento -> toDTO(formaPagamento))
				.collect(Collectors.toList());
	}
	
}
