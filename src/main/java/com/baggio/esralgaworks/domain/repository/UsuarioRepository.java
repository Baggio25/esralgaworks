package com.baggio.esralgaworks.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baggio.esralgaworks.domain.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
