package com.baggio.esralgaworks.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baggio.esralgaworks.domain.exception.CozinhaNaoEncontradaException;
import com.baggio.esralgaworks.domain.exception.EntidadeEmUsoException;
import com.baggio.esralgaworks.domain.exception.RestauranteNaoEncontradoException;
import com.baggio.esralgaworks.domain.model.Cozinha;
import com.baggio.esralgaworks.domain.model.Restaurante;
import com.baggio.esralgaworks.domain.repository.CozinhaRepository;
import com.baggio.esralgaworks.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	private static final String MSG_RESTAURANTE_EM_USO = "Estado de código %d não pode ser removido, pois está em uso.";

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Cozinha cozinha = cozinhaRepository.findById(cozinhaId)
				.orElseThrow(() -> new CozinhaNaoEncontradaException(cozinhaId));

		restaurante.setCozinha(cozinha);
		return restauranteRepository.save(restaurante);
	}

	public void excluir(Long id) {
		try {
			restauranteRepository.deleteById(id);

		} catch (EmptyResultDataAccessException e) {
			throw new RestauranteNaoEncontradoException(id);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_RESTAURANTE_EM_USO, id));

		}
	}

	public Restaurante buscarOuFalhar(Long id) {
		return restauranteRepository.findById(id).orElseThrow(() -> new RestauranteNaoEncontradoException(id));
	}

}
