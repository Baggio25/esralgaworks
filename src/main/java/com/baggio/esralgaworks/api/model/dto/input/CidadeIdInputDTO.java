package com.baggio.esralgaworks.api.model.dto.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeIdInputDTO {

	@NotNull
	private Long id;
	
}
