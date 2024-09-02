package com.baggio.esralgaworks.api.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.baggio.esralgaworks.api.model.assembler.ProdutoDTOAssembler;
import com.baggio.esralgaworks.api.model.disassembler.ProdutoInputDTODisassembler;
import com.baggio.esralgaworks.api.model.dto.ProdutoDTO;
import com.baggio.esralgaworks.api.model.dto.input.ProdutoInputDTO;
import com.baggio.esralgaworks.domain.exception.NegocioException;
import com.baggio.esralgaworks.domain.exception.ProdutoNaoEncontradoException;
import com.baggio.esralgaworks.domain.exception.RestauranteNaoEncontradoException;
import com.baggio.esralgaworks.domain.model.Produto;
import com.baggio.esralgaworks.domain.model.Restaurante;
import com.baggio.esralgaworks.domain.repository.ProdutoRepository;
import com.baggio.esralgaworks.domain.service.CadastroProdutoService;
import com.baggio.esralgaworks.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {

  @Autowired
  private ProdutoRepository produtoRepository;

  @Autowired
  private CadastroProdutoService produtoService;

  @Autowired
  private CadastroRestauranteService restauranteService;

  @Autowired
  private ProdutoDTOAssembler produtoDTOAssembler;

  @Autowired
  private ProdutoInputDTODisassembler produtoInputDTODisassembler;

  @GetMapping
  public ResponseEntity<List<ProdutoDTO>> listar(@PathVariable Long restauranteId) {
    Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
    List<Produto> todosProdutos = produtoRepository.findByRestaurante(restaurante);

    return ResponseEntity.ok(produtoDTOAssembler.toCollectionDTO(todosProdutos));
  }

  @GetMapping("/{produtoId}")
  public ResponseEntity<ProdutoDTO> buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
    Produto produto = produtoService.buscarOuFalhar(restauranteId, produtoId);

    return ResponseEntity.ok(produtoDTOAssembler.toDTO(produto));
  }

  @PostMapping
  public ResponseEntity<ProdutoDTO> salvar(@PathVariable Long restauranteId,
      @RequestBody @Valid ProdutoInputDTO produtoInputDTO) {
    try {

      Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
      Produto produto = produtoInputDTODisassembler.toDomain(produtoInputDTO);

      URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
          .path("/{restauranteId}").path("/{produtoId}")
          .buildAndExpand(restaurante.getId(), produto.getId())
          .toUri();

      produto.setRestaurante(restaurante);
      produto = produtoService.salvar(produto);

      return ResponseEntity.created(uri).body(produtoDTOAssembler.toDTO(produto));
    } catch (RestauranteNaoEncontradoException | ProdutoNaoEncontradoException e) {
      throw new NegocioException(e.getMessage(), e);
    }
  }
 
  @PutMapping("/{produtoId}")
  public ResponseEntity<ProdutoDTO> salvar(@PathVariable Long restauranteId,
      @PathVariable Long produtoId,
      @RequestBody @Valid ProdutoInputDTO produtoInputDTO) {
    
    try {
      Produto produtoAtual = produtoService.buscarOuFalhar(restauranteId, produtoId);
      produtoInputDTODisassembler.copyToDomainObject(produtoInputDTO, produtoAtual);

      produtoAtual = produtoService.salvar(produtoAtual);
      
      return ResponseEntity.ok().body(produtoDTOAssembler.toDTO(produtoAtual));
    } catch (RestauranteNaoEncontradoException | ProdutoNaoEncontradoException e) {
      throw new NegocioException(e.getMessage(), e);
    }
  }

}
