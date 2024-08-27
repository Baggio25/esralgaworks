package com.baggio.esralgaworks.api.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.baggio.esralgaworks.api.model.assembler.UsuarioDTOAssembler;
import com.baggio.esralgaworks.api.model.disassembler.UsuarioInputDTODisassembler;
import com.baggio.esralgaworks.api.model.dto.UsuarioDTO;
import com.baggio.esralgaworks.api.model.dto.input.SenhaInputDTO;
import com.baggio.esralgaworks.api.model.dto.input.UsuarioComSenhaInputDTO;
import com.baggio.esralgaworks.api.model.dto.input.UsuarioInputDTO;
import com.baggio.esralgaworks.domain.model.Usuario;
import com.baggio.esralgaworks.domain.repository.UsuarioRepository;
import com.baggio.esralgaworks.domain.service.CadastroUsuarioService;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {

  @Autowired
  private CadastroUsuarioService usuarioService;

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private UsuarioDTOAssembler usuarioDTOAssembler;

  @Autowired
  private UsuarioInputDTODisassembler usuarioInputDTODisassembler;

  @GetMapping
  public ResponseEntity<List<UsuarioDTO>> listar() {
    List<Usuario> usuarios = usuarioRepository.findAll();
    return ResponseEntity.ok(usuarioDTOAssembler.toCollectionDTO(usuarios));
  }

  @GetMapping("/{usuarioId}")
	public ResponseEntity<UsuarioDTO> buscar(@PathVariable Long usuarioId) {
		Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);
		return ResponseEntity.ok(usuarioDTOAssembler.toDTO(usuario));
	}

  @PostMapping
	public ResponseEntity<UsuarioDTO> salvar(@Valid @RequestBody UsuarioComSenhaInputDTO usuarioInputDTO) {
		Usuario usuario = usuarioInputDTODisassembler.toDomain(usuarioInputDTO);
		usuario = usuarioService.salvar(usuario);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(usuario.getId()).toUri();

		return ResponseEntity.created(uri).body(usuarioDTOAssembler.toDTO(usuario)); 
	}

	@PutMapping(value = "/{usuarioId}")
	public ResponseEntity<UsuarioDTO> atualizar(@PathVariable Long usuarioId, 
			@Valid @RequestBody UsuarioInputDTO usuarioInputDTO) {
    Usuario usuarioAtual = usuarioService.buscarOuFalhar(usuarioId);
		usuarioInputDTODisassembler.copyToDomainObject(usuarioInputDTO, usuarioAtual);
		usuarioAtual = usuarioService.salvar(usuarioAtual);

		return ResponseEntity.ok(usuarioDTOAssembler.toDTO(usuarioAtual));
	}

  @PutMapping(value = "/{usuarioId}/senha")
  public ResponseEntity<Void> alterarSenha(@PathVariable Long usuarioId, 
      @RequestBody @Valid SenhaInputDTO senhaInputDTO){
    usuarioService.alterarSenha(usuarioId, senhaInputDTO.getSenhaAtual(), senhaInputDTO.getNovaSenha());
    return ResponseEntity.noContent().build();
  }

}
