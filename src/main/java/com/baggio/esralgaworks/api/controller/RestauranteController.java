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
		Optional<Restaurante> restauranteOpt = restauranteRepository.findById(id);

		if (restauranteOpt.isPresent()) {
			return ResponseEntity.ok(restauranteOpt.get());
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<?> salvar(@RequestBody Restaurante restaurante) {
		try {
			restaurante = restauranteService.salvar(restaurante);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(restaurante.getId())
					.toUri();

			return ResponseEntity.created(uri).body(restaurante);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Restaurante restaurante) {
		try {
			Optional<Restaurante> restauranteOpt = restauranteRepository.findById(id);

			if (restauranteOpt.isPresent()) {
				BeanUtils.copyProperties(restaurante, restauranteOpt.get(), "id", "formasPagamento");

				Restaurante restauranteSalva = restauranteService.salvar(restauranteOpt.get());
				return ResponseEntity.ok(restauranteSalva);
			}

			return ResponseEntity.notFound().build();
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> excluir(@PathVariable Long id) {
		try {
			restauranteService.excluir(id);
			return ResponseEntity.noContent().build();

		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());

		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}
	}

}
