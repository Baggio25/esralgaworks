package com.baggio.esralgaworks.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baggio.esralgaworks.domain.model.Cozinha;
import com.baggio.esralgaworks.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {
  
  @Autowired
  private CozinhaRepository cozinhaRepository;

  public Cozinha salvar(Cozinha cozinha) {
    return cozinhaRepository.salvar(cozinha);
  }

}
