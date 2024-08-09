package com.baggio.esralgaworks.api.model.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baggio.esralgaworks.api.model.dto.input.FormaPagamentoInputDTO;
import com.baggio.esralgaworks.domain.model.FormaPagamento;

@Component
public class FormaPagamentoInputDTODisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public FormaPagamento toDomain(FormaPagamentoInputDTO formaPagamentoInputDTO) {
		return modelMapper.map(formaPagamentoInputDTO, FormaPagamento.class);
	}
	
	public void copyToDomainObject(FormaPagamentoInputDTO formaPagamentoInputDTO, FormaPagamento formaPagamento) {
        modelMapper.map(formaPagamentoInputDTO, formaPagamento);
    }
}
