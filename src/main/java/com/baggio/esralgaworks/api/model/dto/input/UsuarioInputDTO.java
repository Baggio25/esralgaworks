package com.baggio.esralgaworks.api.model.dto.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioInputDTO {

	@NotBlank
	private String nome;
	
	@NotBlank
	private String email;
	
}
