package com.baggio.esralgaworks.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baggio.esralgaworks.domain.exception.EntidadeEmUsoException;
import com.baggio.esralgaworks.domain.exception.NegocioException;
import com.baggio.esralgaworks.domain.exception.UsuarioNaoEncontradoException;
import com.baggio.esralgaworks.domain.model.Usuario;
import com.baggio.esralgaworks.domain.repository.UsuarioRepository;

@Service
public class CadastroUsuarioService {

	private static final String MSG_ESTADO_EM_USO = "Usuario de código %d não pode ser removido, pois está em uso.";
	
	@Autowired
	private UsuarioRepository UsuarioRepository;

	@Transactional
	public Usuario salvar(Usuario Usuario) {
		return UsuarioRepository.save(Usuario);
	}

	public void alterarSenha(Long usuarioId, String senhaAtual, String novaSenha) {
		Usuario usuario = buscarOuFalhar(usuarioId);

		if(usuario.senhaNaoCoincideCom(senhaAtual)) {
			throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
		}

		usuario.setSenha(novaSenha);
	}

	public Usuario buscarOuFalhar(Long id) {
		return UsuarioRepository.findById(id)
				.orElseThrow(() -> new UsuarioNaoEncontradoException(id));
	}

}
