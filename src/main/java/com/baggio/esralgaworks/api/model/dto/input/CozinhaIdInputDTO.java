package com.baggio.esralgaworks.api.model.dto.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CozinhaIdInputDTO {
	
	@NotNull
	private Long id;
}
