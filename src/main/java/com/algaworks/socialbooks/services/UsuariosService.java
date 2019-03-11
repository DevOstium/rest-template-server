package com.algaworks.socialbooks.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.socialbooks.domain.Usuario;
import com.algaworks.socialbooks.exceptions.UsuarioExistenteException;
import com.algaworks.socialbooks.exceptions.UsuarioNaoEncontratoExceptio;
import com.algaworks.socialbooks.repository.UsuariosRepository;

@Service
public class UsuariosService {

	@Autowired
	private UsuariosRepository usuariosRepository;

	public List<Usuario> listar() {
		return usuariosRepository.findAll();
	}

	public Usuario salvar(Usuario usuario) {

		if (usuario.getId() != null) {
			Usuario u = usuariosRepository.findOne(usuario.getId());

			if (u != null) {
				throw new UsuarioExistenteException("Usuario já existe!");
			}
		}
		return usuariosRepository.save(usuario);
	}

	public Usuario buscarPorId(Long id) {

		Usuario usuario = usuariosRepository.findOne(id);

		if (usuario == null) {
			throw new UsuarioNaoEncontratoExceptio("O usuario não pode ser encontrado.");
		}
		return usuario;
	}

	public void deletar (Long id){
		try {
			usuariosRepository.delete(id);
		} catch (EmptyResultDataAccessException e){
			throw new UsuarioNaoEncontratoExceptio("O usuario não foi encontrado!");
		}
	}
	
	public void atualizar(Usuario usuario){
		verificaExistencia(usuario);
		usuariosRepository.save(usuario);
	}
	
	public void verificaExistencia(Usuario usuario){
		buscarPorId(usuario.getId());
	}
	
	
	
}
