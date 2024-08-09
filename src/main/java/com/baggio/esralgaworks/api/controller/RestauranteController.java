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

import com.baggio.esralgaworks.api.model.assembler.RestauranteDTOAssembler;
import com.baggio.esralgaworks.api.model.disassembler.RestauranteInputDTODisassembler;
import com.baggio.esralgaworks.api.model.dto.RestauranteDTO;
import com.baggio.esralgaworks.api.model.dto.input.RestauranteInputDTO;
import com.baggio.esralgaworks.domain.exception.CozinhaNaoEncontradaException;
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
	
	@Autowired
	private RestauranteDTOAssembler restauranteDTOAssembler;
	
	@Autowired
	private RestauranteInputDTODisassembler restauranteInputDTODisassembler;

	@GetMapping
	public ResponseEntity<List<RestauranteDTO>> listar() {
		List<Restaurante> restaurantes = restauranteRepository.findAll();
		return ResponseEntity.ok(restauranteDTOAssembler.toCollectionDTO(restaurantes));
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<RestauranteDTO> buscar(@PathVariable Long id) {
		Restaurante restaurante = restauranteService.buscarOuFalhar(id);
		return ResponseEntity.ok(restauranteDTOAssembler.toDTO(restaurante));
	}

	@PostMapping
	public ResponseEntity<RestauranteDTO> salvar(@Valid @RequestBody RestauranteInputDTO restauranteInputDTO) {
		try {
			Restaurante restaurante = restauranteInputDTODisassembler.toDomain(restauranteInputDTO);					
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
					.path("/{id}").buildAndExpand(restaurante.getId())
					.toUri();
			RestauranteDTO restauranteDTO = restauranteDTOAssembler.toDTO(restauranteService.salvar(restaurante));
			
			return ResponseEntity.created(uri).body(restauranteDTO);
		} catch (CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<RestauranteDTO> atualizar(@PathVariable Long id, 
			@Valid @RequestBody RestauranteInputDTO restauranteInputDTO) {
		try {		
			Restaurante restaurante = restauranteInputDTODisassembler.toDomain(restauranteInputDTO);
			Restaurante restauranteAtual = restauranteService.buscarOuFalhar(id);
			BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "dataCadastro");

			restauranteService.salvar(restauranteAtual);
			
			return ResponseEntity.ok(restauranteDTOAssembler.toDTO(restauranteAtual));
		} catch (CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long id) {
		restauranteService.excluir(id);
	}
	
	@PutMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable Long restauranteId) {
		restauranteService.ativar(restauranteId);
	}

	@DeleteMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable Long restauranteId) {
		restauranteService.inativar(restauranteId);
	}
}
