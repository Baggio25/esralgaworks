package com.baggio.esralgaworks.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.dao.EmptyResultDataAccessException;

import com.baggio.esralgaworks.domain.model.Restaurante;
import com.baggio.esralgaworks.domain.repository.RestauranteRepository;

public class RestauranteRepositoryImpl implements RestauranteRepository{

  @PersistenceContext
  private EntityManager manager;

  @Override
  public List<Restaurante> listar() {
    return manager.createQuery("from Restaurante", Restaurante.class).getResultList();
  }

  @Override
  public Restaurante buscar(Long id) {
    return manager.find(Restaurante.class, id);
  }

  @Override
  public Restaurante salvar(Restaurante restaurante) {
    return manager.merge(restaurante);
  }

  @Override
  public void remover(Long id) {
    Restaurante restaurante = buscar(id);

    if(restaurante == null) {
      throw new EmptyResultDataAccessException(1);
    }

    manager.remove(restaurante); 
  }
  
}
