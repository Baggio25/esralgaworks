package com.baggio.esralgaworks.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baggio.esralgaworks.domain.exception.CozinhaNaoEncontradaException;
import com.baggio.esralgaworks.domain.exception.EntidadeEmUsoException;
import com.baggio.esralgaworks.domain.model.Cozinha;
import com.baggio.esralgaworks.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {

	private static final String MSG_COZINHA_EM_USO = "Cozinha de código %d não pode ser removida, pois está em uso.";
	
	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Transactional
	public Cozinha salvar(Cozinha cozinha) {
		return cozinhaRepository.save(cozinha);
	}

	public void excluir(Long id) {
		try {
			cozinhaRepository.deleteById(id);

		} catch (EmptyResultDataAccessException e) {
			throw new CozinhaNaoEncontradaException(id);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_COZINHA_EM_USO, id));

		}
	}

	public Cozinha buscarOuFalhar(Long id) {
		return cozinhaRepository.findById(id)
				.orElseThrow(() -> new CozinhaNaoEncontradaException(id));
	}
}
