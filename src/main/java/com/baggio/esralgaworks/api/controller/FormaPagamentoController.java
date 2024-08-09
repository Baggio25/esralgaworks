package com.baggio.esralgaworks.api.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.baggio.esralgaworks.api.model.assembler.FormaPagamentoDTOAssembler;
import com.baggio.esralgaworks.api.model.disassembler.FormaPagamentoInputDTODisassembler;
import com.baggio.esralgaworks.api.model.dto.FormaPagamentoDTO;
import com.baggio.esralgaworks.api.model.dto.input.FormaPagamentoInputDTO;
import com.baggio.esralgaworks.domain.model.FormaPagamento;
import com.baggio.esralgaworks.domain.repository.FormaPagamentoRepository;
import com.baggio.esralgaworks.domain.service.CadastroFormaPagamentoService;

@RestController
@RequestMapping(value = "/formasPagamento")
public class FormaPagamentoController {

	@Autowired
	private CadastroFormaPagamentoService formaPagamentoService;

	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;
	
	@Autowired
	private FormaPagamentoDTOAssembler formaPagamentoDTOAssembler;
	
	@Autowired
	private FormaPagamentoInputDTODisassembler formaPagamentoInputDTODisassembler;

	@GetMapping
	public ResponseEntity<List<FormaPagamentoDTO>> listar() {
		List<FormaPagamento> formasPagamento = formaPagamentoRepository.findAll();
		return ResponseEntity.ok(formaPagamentoDTOAssembler.toCollectionDTO(formasPagamento));
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<FormaPagamentoDTO> buscar(@PathVariable Long id) {
		FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalhar(id);
		return ResponseEntity.ok(formaPagamentoDTOAssembler.toDTO(formaPagamento));
	}

	@PostMapping
	public ResponseEntity<FormaPagamentoDTO> salvar(@Valid @RequestBody FormaPagamentoInputDTO formaPagamentoInputDTO) {
		FormaPagamento formaPagamento = formaPagamentoInputDTODisassembler.toDomain(formaPagamentoInputDTO);
		formaPagamento = formaPagamentoService.salvar(formaPagamento);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(formaPagamento.getId()).toUri();

		return ResponseEntity.created(uri).body(formaPagamentoDTOAssembler.toDTO(formaPagamento));
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<FormaPagamentoDTO> atualizar(@PathVariable Long id, 
			@Valid @RequestBody FormaPagamentoInputDTO formaPagamentoInputDTO) {
		FormaPagamento formaPagamentoAtual = formaPagamentoService.buscarOuFalhar(id);
		formaPagamentoInputDTODisassembler.copyToDomainObject(formaPagamentoInputDTO, formaPagamentoAtual);
		formaPagamentoAtual = formaPagamentoService.salvar(formaPagamentoAtual);

		return ResponseEntity.ok(formaPagamentoDTOAssembler.toDTO(formaPagamentoAtual));
	}

	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long id) {
		formaPagamentoService.excluir(id);
	}

}
