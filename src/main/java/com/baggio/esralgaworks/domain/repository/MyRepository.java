package com.baggio.esralgaworks.domain.repository;

import java.util.List;

public interface MyRepository<T> {

    List<T> listar();
    T buscar(Long id);
    T salvar(T t);
    void remover(T t);

}
