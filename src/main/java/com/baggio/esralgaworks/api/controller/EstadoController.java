package com.baggio.esralgaworks.api.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.baggio.esralgaworks.domain.exception.EntidadeEmUsoException;
import com.baggio.esralgaworks.domain.exception.EntidadeNaoEncontradaException;
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
		Optional<Estado> estadoOpt = estadoRepository.findById(id);

		if (estadoOpt.isPresent()) {
			return ResponseEntity.ok(estadoOpt.get());
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<Estado> salvar(@RequestBody Estado estado) {
		estado = estadoService.salvar(estado);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(estado.getId()).toUri();

		return ResponseEntity.created(uri).body(estado);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Estado> atualizar(@PathVariable Long id, @RequestBody Estado estado) {
		Optional<Estado> estadoOpt = estadoRepository.findById(id);

		if (estadoOpt.isPresent()) {
			BeanUtils.copyProperties(estado, estadoOpt.get(), "id");

			Estado estadoSalvo = estadoService.salvar(estadoOpt.get());
			return ResponseEntity.ok(estadoSalvo);
		}

		return ResponseEntity.notFound().build();
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> excluir(@PathVariable Long id) {
		try {
			estadoService.excluir(id);
			return ResponseEntity.noContent().build();

		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());

		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}
	}

}
