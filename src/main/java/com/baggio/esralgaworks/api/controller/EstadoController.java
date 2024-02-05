package com.baggio.esralgaworks.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baggio.esralgaworks.domain.model.Estado;
import com.baggio.esralgaworks.domain.repository.EstadoRepository;

@RestController
@RequestMapping(value = "/estados")
public class EstadoController {

  @Autowired
  private EstadoRepository estadoRepository;

  @GetMapping
  private List<Estado> listar() {
    return estadoRepository.listar();
  }

  
}
