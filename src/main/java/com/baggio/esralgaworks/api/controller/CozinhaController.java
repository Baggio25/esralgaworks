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

import com.baggio.esralgaworks.api.model.assembler.CozinhaDTOAssembler;
import com.baggio.esralgaworks.api.model.disassembler.CozinhaInputDTODisassembler;
import com.baggio.esralgaworks.api.model.dto.CozinhaDTO;
import com.baggio.esralgaworks.api.model.dto.input.CozinhaInputDTO;
import com.baggio.esralgaworks.domain.model.Cozinha;
import com.baggio.esralgaworks.domain.repository.CozinhaRepository;
import com.baggio.esralgaworks.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController {

	@Autowired
	private CadastroCozinhaService cozinhaService;

	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private CozinhaDTOAssembler cozinhaDTOAssembler;
	
	@Autowired
	private CozinhaInputDTODisassembler cozinhaInputDTODisassembler;

	@GetMapping
	public ResponseEntity<List<CozinhaDTO>> listar() {
		List<Cozinha> cozinhas = cozinhaRepository.findAll();		
		return ResponseEntity.ok(cozinhaDTOAssembler.toCollectionDTO(cozinhas));
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<CozinhaDTO> buscar(@PathVariable Long id) {
		Cozinha cozinha = cozinhaService.buscarOuFalhar(id);
		return ResponseEntity.ok(cozinhaDTOAssembler.toDTO(cozinha));
	}

	@PostMapping
	public ResponseEntity<CozinhaDTO> salvar(@Valid @RequestBody CozinhaInputDTO cozinhaInputDTO) {
		Cozinha cozinha = cozinhaInputDTODisassembler.toDomain(cozinhaInputDTO);
		cozinha = cozinhaService.salvar(cozinha);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
					.path("/{id}").buildAndExpand(cozinha.getId()).toUri();

		return ResponseEntity.created(uri).body(cozinhaDTOAssembler.toDTO(cozinha));
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<CozinhaDTO> atualizar(@PathVariable Long id, 
			@Valid @RequestBody CozinhaInputDTO cozinhaInputDTO) {
		Cozinha cozinhaAtual = cozinhaService.buscarOuFalhar(id);
		cozinhaInputDTODisassembler.copyToDomainObject(cozinhaInputDTO, cozinhaAtual);
		cozinhaAtual = cozinhaService.salvar(cozinhaAtual);
		
		return ResponseEntity.ok(cozinhaDTOAssembler.toDTO(cozinhaAtual));
	}

	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long id) {
		cozinhaService.excluir(id);
	}

}
