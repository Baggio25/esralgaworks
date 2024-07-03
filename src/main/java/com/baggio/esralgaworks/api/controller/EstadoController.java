package com.baggio.esralgaworks.api.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
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

	@GetMapping
	public ResponseEntity<List<Estado>> listar() {
		List<Estado> estados = estadoRepository.findAll();
		return ResponseEntity.ok(estados);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Estado> buscar(@PathVariable Long id) {
		Estado estado = estadoService.buscarOuFalhar(id);
		return ResponseEntity.ok(estado);
	}

	@PostMapping
	public ResponseEntity<Estado> salvar(@Valid @RequestBody Estado estado) {
		estado = estadoService.salvar(estado);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(estado.getId()).toUri();

		return ResponseEntity.created(uri).body(estado);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Estado> atualizar(@PathVariable Long id, @Valid @RequestBody Estado estado) {
		Estado estadoAtual = estadoService.buscarOuFalhar(id);
		BeanUtils.copyProperties(estado, estadoAtual, "id");
		estadoService.salvar(estadoAtual);

		return ResponseEntity.ok(estadoAtual);
	}

	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long id) {
		estadoService.excluir(id);
	}

}
