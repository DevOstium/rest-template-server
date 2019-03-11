package com.algaworks.socialbooks.exceptions;

public class UsuarioNaoEncontratoExceptio extends RuntimeException  {

	private static final long serialVersionUID = 1L;

	public UsuarioNaoEncontratoExceptio(String mensagem) {
		super(mensagem);
	}

	public UsuarioNaoEncontratoExceptio(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}

}
