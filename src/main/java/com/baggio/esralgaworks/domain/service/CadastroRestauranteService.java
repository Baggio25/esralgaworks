package com.baggio.esralgaworks.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.baggio.esralgaworks.domain.exception.EntidadeEmUsoException;
import com.baggio.esralgaworks.domain.exception.EntidadeNaoEncontradaException;
import com.baggio.esralgaworks.domain.model.Restaurante;
import com.baggio.esralgaworks.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {
  
  @Autowired
  private RestauranteRepository restauranteRepository;

  public Restaurante salvar(Restaurante restaurante) {
    return restauranteRepository.salvar(restaurante);
  }
  
  public void excluir(Long id) {
    try {
      restauranteRepository.remover(id);

    } catch (EmptyResultDataAccessException e) {
      throw new EntidadeNaoEncontradaException(String
          .format("Restaurante de código %d não foi encontrada.", id));  
    
    } catch (DataIntegrityViolationException e) {
      throw new EntidadeEmUsoException(String
          .format("Restaurante de código %d não pode ser removida, pois está em uso.", id));
      
    }
  }

}
