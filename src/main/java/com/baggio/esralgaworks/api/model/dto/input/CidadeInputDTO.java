package com.baggio.esralgaworks.api.model.dto.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeInputDTO {

	@NotBlank
    private String nome;

	@Valid
    @NotNull
    private EstadoIdInputDTO estado;
	
}
