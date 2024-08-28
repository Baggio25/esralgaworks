package com.baggio.esralgaworks.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baggio.esralgaworks.api.model.assembler.FormaPagamentoDTOAssembler;
import com.baggio.esralgaworks.api.model.dto.FormaPagamentoDTO;
import com.baggio.esralgaworks.domain.model.Restaurante;
import com.baggio.esralgaworks.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/formas-pagamento")
public class RestauranteFormaPagamentoController {

	@Autowired
	private CadastroRestauranteService restauranteService;

	@Autowired
	private FormaPagamentoDTOAssembler formaPagamentoDTOAssembler;

	@GetMapping
	public ResponseEntity<List<FormaPagamentoDTO>> listar(@PathVariable Long restauranteId) {
		Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
		return ResponseEntity.ok(formaPagamentoDTOAssembler.toCollectionDTO(restaurante.getFormasPagamento()));
	}

	@PutMapping("/{formaPagamentoId}")
	public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
		restauranteService.associarFormaPagamento(restauranteId, formaPagamentoId);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{formaPagamentoId}")
	public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
		restauranteService.desassociarFormaPagamento(restauranteId, formaPagamentoId);
		return ResponseEntity.noContent().build();
	}

}
