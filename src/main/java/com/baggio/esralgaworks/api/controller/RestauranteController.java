package com.baggio.esralgaworks.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    List<Restaurante> listaRestaurantes = restauranteRepository.listar();
    return ResponseEntity.ok(listaRestaurantes);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<Restaurante> buscar(@PathVariable Long id) {
    Restaurante restaurante = restauranteRepository.buscar(id);
    return ResponseEntity.ok(restaurante);
  }


}
