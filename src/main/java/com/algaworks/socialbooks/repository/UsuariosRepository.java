package com.algaworks.socialbooks.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.socialbooks.domain.Usuario;

public interface UsuariosRepository extends JpaRepository<Usuario, Long>{

}
