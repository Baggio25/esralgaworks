package com.baggio.esralgaworks.api.controller;

import java.net.URI;
import java.util.List;

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

import com.baggio.esralgaworks.domain.exception.EntidadeNaoEncontradaException;
import com.baggio.esralgaworks.domain.exception.NegocioException;
import com.baggio.esralgaworks.domain.model.Restaurante;
import com.baggio.esralgaworks.domain.repository.RestauranteRepository;
import com.baggio.esralgaworks.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteController {

	@Autowired
	private CadastroRestauranteService restauranteService;

	@Autowired
	private RestauranteRepository restauranteRepository;

	@GetMapping
	public ResponseEntity<List<Restaurante>> listar() {
		List<Restaurante> restaurantes = restauranteRepository.findAll();
		return ResponseEntity.ok(restaurantes);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Restaurante> buscar(@PathVariable Long id) {
		Restaurante restaurante = restauranteService.buscarOuFalhar(id);
		return ResponseEntity.ok(restaurante);
	}

	@PostMapping
	public ResponseEntity<?> salvar(@RequestBody Restaurante restaurante) {
		try {
			restaurante = restauranteService.salvar(restaurante);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(restaurante.getId())
					.toUri();

			return ResponseEntity.created(uri).body(restaurante);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Restaurante restaurante) {
		Restaurante restauranteAtual = restauranteService.buscarOuFalhar(id);
		BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "dataCadastro");

		try {
			restauranteService.salvar(restauranteAtual);
			return ResponseEntity.ok(restauranteAtual);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long id) {
		restauranteService.excluir(id);
	}

}
