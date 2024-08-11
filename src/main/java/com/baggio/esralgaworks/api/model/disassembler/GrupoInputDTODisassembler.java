package com.baggio.esralgaworks.api.model.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baggio.esralgaworks.api.model.dto.input.GrupoInputDTO;
import com.baggio.esralgaworks.domain.model.Grupo;

@Component
public class GrupoInputDTODisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Grupo toDomain(GrupoInputDTO grupoInputDTO) {
		return modelMapper.map(grupoInputDTO, Grupo.class);
	}
	
	public void copyToDomainObject(GrupoInputDTO grupoInputDTO, Grupo grupo) {
        modelMapper.map(grupoInputDTO, grupo);
    }
}
