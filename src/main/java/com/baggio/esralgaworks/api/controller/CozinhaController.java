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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.baggio.esralgaworks.domain.exception.EntidadeEmUsoException;
import com.baggio.esralgaworks.domain.exception.EntidadeNaoEncontradaException;
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

  @GetMapping
  public ResponseEntity<List<Cozinha>> listar() {
    List<Cozinha> cozinhas = cozinhaRepository.listar();
    return ResponseEntity.ok(cozinhas);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<Cozinha> buscar(@PathVariable Long id) {
    Cozinha cozinha = cozinhaRepository.buscar(id);
    return ResponseEntity.ok(cozinha);
  }

  @PostMapping
  public ResponseEntity<Cozinha> salvar(@RequestBody Cozinha cozinha) {
    cozinha = cozinhaService.salvar(cozinha);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(cozinha.getId()).toUri();

    return ResponseEntity.created(uri).body(cozinha);
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<Cozinha> atualizar(@PathVariable Long id, @RequestBody Cozinha cozinha) {
    Cozinha cozinhaAtual = cozinhaRepository.buscar(id);
    BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");

    cozinhaAtual = cozinhaService.salvar(cozinhaAtual);
   
    return ResponseEntity.ok(cozinhaAtual);
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> excluir(@PathVariable Long id) {
    try {
      cozinhaService.excluir(id);
      return ResponseEntity.noContent().build();
      
    } catch (EntidadeEmUsoException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();

    } catch (EntidadeNaoEncontradaException e) {
      return ResponseEntity.notFound().build();
    }
  }

}
