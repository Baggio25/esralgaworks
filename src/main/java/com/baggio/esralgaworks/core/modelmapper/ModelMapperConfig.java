package com.baggio.esralgaworks.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baggio.esralgaworks.api.model.dto.EnderecoDTO;
import com.baggio.esralgaworks.domain.model.Endereco;

@Configuration
public class ModelMapperConfig {
	
	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		
		/*
		 * var enderecoToEnderecoDTOTypeMap = modelMapper.createTypeMap( Endereco.class,
		 * EnderecoDTO.class);
		 * 
		 * enderecoToEnderecoDTOTypeMap.<String>addMapping( src ->
		 * src.getCidade().getEstado().getNome(), (dest, value) ->
		 * dest.getCidade().setEstado(value));
		 */
		return new ModelMapper();
	}
	
}
