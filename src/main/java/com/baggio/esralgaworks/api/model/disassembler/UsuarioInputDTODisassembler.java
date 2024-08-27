package com.baggio.esralgaworks.api.model.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baggio.esralgaworks.api.model.dto.input.UsuarioInputDTO;
import com.baggio.esralgaworks.domain.model.Usuario;

@Component
public class UsuarioInputDTODisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Usuario toDomain(UsuarioInputDTO usuarioInputDTO) {
		return modelMapper.map(usuarioInputDTO, Usuario.class);
	}
	
	public void copyToDomainObject(UsuarioInputDTO usuarioInputDTO, Usuario usuario) {
        modelMapper.map(usuarioInputDTO, usuario);
    }
}
