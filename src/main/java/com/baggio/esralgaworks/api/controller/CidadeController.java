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

import com.baggio.esralgaworks.api.model.assembler.CidadeDTOAssembler;
import com.baggio.esralgaworks.api.model.disassembler.CidadeInputDTODisassembler;
import com.baggio.esralgaworks.api.model.dto.CidadeDTO;
import com.baggio.esralgaworks.api.model.dto.input.CidadeInputDTO;
import com.baggio.esralgaworks.domain.exception.EntidadeNaoEncontradaException;
import com.baggio.esralgaworks.domain.exception.EstadoNaoEncontradoException;
import com.baggio.esralgaworks.domain.exception.NegocioException;
import com.baggio.esralgaworks.domain.model.Cidade;
import com.baggio.esralgaworks.domain.repository.CidadeRepository;
import com.baggio.esralgaworks.domain.service.CadastroCidadeService;

@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {

	@Autowired
	private CadastroCidadeService cidadeService;

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CidadeDTOAssembler cidadeDTOAssembler;
	
	@Autowired
	private CidadeInputDTODisassembler cidadeInputDTODisassembler;

	@GetMapping
	public ResponseEntity<List<CidadeDTO>> listar() {
		List<Cidade> cidades = cidadeRepository.findAll();
		return ResponseEntity.ok(cidadeDTOAssembler.toCollectionDTO(cidades));
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<CidadeDTO> buscar(@PathVariable Long id) {
		Cidade cidade = cidadeService.buscarOuFalhar(id);
		return ResponseEntity.ok(cidadeDTOAssembler.toDTO(cidade));
	}

	@PostMapping
	public ResponseEntity<CidadeDTO> salvar(@Valid @RequestBody CidadeInputDTO cidadeInputDTO) {
		try {
			Cidade cidade = cidadeInputDTODisassembler.toDomain(cidadeInputDTO);
			cidade = cidadeService.salvar(cidade);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
					.path("/{id}").buildAndExpand(cidade.getId())
					.toUri();

			return ResponseEntity.created(uri).body(cidadeDTOAssembler.toDTO(cidade));
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<CidadeDTO> atualizar(@PathVariable Long id, 
			@Valid @RequestBody CidadeInputDTO cidadeInputDTO) {
		try {
			Cidade cidadeAtual = cidadeService.buscarOuFalhar(id);
			cidadeInputDTODisassembler.copyToDomainObject(cidadeInputDTO, cidadeAtual);
			cidadeAtual = cidadeService.salvar(cidadeAtual);
			
			return ResponseEntity.ok(cidadeDTOAssembler.toDTO(cidadeAtual));
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long id) {
		cidadeService.excluir(id);
	}

}
