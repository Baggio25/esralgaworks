package com.baggio.esralgaworks.api.model.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baggio.esralgaworks.api.model.dto.input.CidadeInputDTO;
import com.baggio.esralgaworks.domain.model.Cidade;

@Component
public class CidadeInputDTODisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Cidade toDomain(CidadeInputDTO cidadeInputDTO) {
		return modelMapper.map(cidadeInputDTO, Cidade.class);
	}
	
	public void copyToDomainObject(CidadeInputDTO cidadeInputDTO, Cidade cidade) {
        modelMapper.map(cidadeInputDTO, cidade);
    }
}
