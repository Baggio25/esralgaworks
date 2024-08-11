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

import com.baggio.esralgaworks.api.model.assembler.GrupoDTOAssembler;
import com.baggio.esralgaworks.api.model.disassembler.GrupoInputDTODisassembler;
import com.baggio.esralgaworks.api.model.dto.GrupoDTO;
import com.baggio.esralgaworks.api.model.dto.input.GrupoInputDTO;
import com.baggio.esralgaworks.domain.model.Grupo;
import com.baggio.esralgaworks.domain.repository.GrupoRepository;
import com.baggio.esralgaworks.domain.service.CadastroGrupoService;

@RestController
@RequestMapping(value = "/grupos")
public class GrupoController {

	@Autowired
	private CadastroGrupoService grupoService;

	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private GrupoDTOAssembler grupoDTOAssembler;
	
	@Autowired
	private GrupoInputDTODisassembler grupoInputDTODisassembler;

	@GetMapping
	public ResponseEntity<List<GrupoDTO>> listar() {
		List<Grupo> grupos = grupoRepository.findAll();
		return ResponseEntity.ok(grupoDTOAssembler.toCollectionDTO(grupos));
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<GrupoDTO> buscar(@PathVariable Long id) {
		Grupo grupo = grupoService.buscarOuFalhar(id);
		return ResponseEntity.ok(grupoDTOAssembler.toDTO(grupo));
	}

	@PostMapping
	public ResponseEntity<GrupoDTO> salvar(@Valid @RequestBody GrupoInputDTO grupoInputDTO) {
		Grupo grupo = grupoInputDTODisassembler.toDomain(grupoInputDTO);
		grupo = grupoService.salvar(grupo);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(grupo.getId()).toUri();

		return ResponseEntity.created(uri).body(grupoDTOAssembler.toDTO(grupo));
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<GrupoDTO> atualizar(@PathVariable Long id, 
			@Valid @RequestBody GrupoInputDTO grupoInputDTO) {
		Grupo grupoAtual = grupoService.buscarOuFalhar(id);
		grupoInputDTODisassembler.copyToDomainObject(grupoInputDTO, grupoAtual);
		grupoAtual = grupoService.salvar(grupoAtual);

		return ResponseEntity.ok(grupoDTOAssembler.toDTO(grupoAtual));
	}

	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long id) {
		grupoService.excluir(id);
	}

}
