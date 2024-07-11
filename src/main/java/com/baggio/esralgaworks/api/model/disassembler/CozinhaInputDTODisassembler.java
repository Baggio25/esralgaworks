package com.baggio.esralgaworks.api.model.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baggio.esralgaworks.api.model.dto.input.CozinhaInputDTO;
import com.baggio.esralgaworks.domain.model.Cozinha;

@Component
public class CozinhaInputDTODisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Cozinha toDomain(CozinhaInputDTO cozinhaInputDTO) {
		return modelMapper.map(cozinhaInputDTO, Cozinha.class);
	}
	
	public void copyToDomainObject(CozinhaInputDTO cozinhaInputDTO, Cozinha cozinha) {
        modelMapper.map(cozinhaInputDTO, cozinha);
    }
}
