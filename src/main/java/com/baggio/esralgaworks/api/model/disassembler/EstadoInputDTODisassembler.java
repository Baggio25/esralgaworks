package com.baggio.esralgaworks.api.model.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baggio.esralgaworks.api.model.dto.input.EstadoInputDTO;
import com.baggio.esralgaworks.domain.model.Estado;

@Component
public class EstadoInputDTODisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Estado toDomain(EstadoInputDTO estadoInputDTO) {
		return modelMapper.map(estadoInputDTO, Estado.class);
	}
	
	public void copyToDomainObject(EstadoInputDTO estadoInputDTO, Estado estado) {
        modelMapper.map(estadoInputDTO, estado);
    }
}
