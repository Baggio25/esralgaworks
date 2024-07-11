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

import com.baggio.esralgaworks.api.model.assembler.EstadoDTOAssembler;
import com.baggio.esralgaworks.api.model.disassembler.EstadoInputDTODisassembler;
import com.baggio.esralgaworks.api.model.dto.EstadoDTO;
import com.baggio.esralgaworks.api.model.dto.input.EstadoInputDTO;
import com.baggio.esralgaworks.domain.model.Estado;
import com.baggio.esralgaworks.domain.repository.EstadoRepository;
import com.baggio.esralgaworks.domain.service.CadastroEstadoService;

@RestController
@RequestMapping(value = "/estados")
public class EstadoController {

	@Autowired
	private CadastroEstadoService estadoService;

	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private EstadoDTOAssembler estadoDTOAssembler;
	
	@Autowired
	private EstadoInputDTODisassembler estadoInputDTODisassembler;

	@GetMapping
	public ResponseEntity<List<EstadoDTO>> listar() {
		List<Estado> estados = estadoRepository.findAll();
		return ResponseEntity.ok(estadoDTOAssembler.toCollectionDTO(estados));
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<EstadoDTO> buscar(@PathVariable Long id) {
		Estado estado = estadoService.buscarOuFalhar(id);
		return ResponseEntity.ok(estadoDTOAssembler.toDTO(estado));
	}

	@PostMapping
	public ResponseEntity<EstadoDTO> salvar(@Valid @RequestBody EstadoInputDTO estadoInputDTO) {
		Estado estado = estadoInputDTODisassembler.toDomain(estadoInputDTO);
		estado = estadoService.salvar(estado);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(estado.getId()).toUri();

		return ResponseEntity.created(uri).body(estadoDTOAssembler.toDTO(estado));
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<EstadoDTO> atualizar(@PathVariable Long id, 
			@Valid @RequestBody EstadoInputDTO estadoInputDTO) {
		Estado estadoAtual = estadoService.buscarOuFalhar(id);
		estadoInputDTODisassembler.copyToDomainObject(estadoInputDTO, estadoAtual);
		estadoAtual = estadoService.salvar(estadoAtual);

		return ResponseEntity.ok(estadoDTOAssembler.toDTO(estadoAtual));
	}

	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long id) {
		estadoService.excluir(id);
	}

}
