package com.baggio.esralgaworks.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.baggio.esralgaworks.domain.exception.EntidadeEmUsoException;
import com.baggio.esralgaworks.domain.exception.EntidadeNaoEncontradaException;
import com.baggio.esralgaworks.domain.model.Cozinha;
import com.baggio.esralgaworks.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {
  
  @Autowired
  private CozinhaRepository cozinhaRepository;

  public Cozinha salvar(Cozinha cozinha) {
    return cozinhaRepository.salvar(cozinha);
  }

  public void excluir(Long id) {
    try {
      cozinhaRepository.remover(id);

    } catch (EmptyResultDataAccessException e) {
      throw new EntidadeNaoEncontradaException(String
          .format("Cozinha de código %d não foi encontrada.", id));  
    
    } catch (DataIntegrityViolationException e) {
      throw new EntidadeEmUsoException(String
          .format("Cozinha de código %d não pode ser removida, pois está em uso.", id));
      
    }
  }

}
