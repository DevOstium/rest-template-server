package com.algaworks.socialbooks.resources;

import java.net.URI;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.algaworks.socialbooks.domain.Autor;
import com.algaworks.socialbooks.domain.Comentario;
import com.algaworks.socialbooks.domain.Livro;
import com.algaworks.socialbooks.services.LivrosService;
import com.hazelcast.security.SecurityContext;

@Controller
@RestController
@RequestMapping(value = "/livros")
public class LivrosResources {

	@Autowired
	private LivrosService livroService;

	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, 
			        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<List<Livro>> listar() {
		List<Livro> livros = livroService.listar();
		return ResponseEntity.status(HttpStatus.OK).body(livros);
	}

	
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> salvar(@Valid @RequestBody Livro livro) {
		livro = livroService.salvar(livro); 
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(livro.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	
	
	//<?> significa que posso encapsular qualquer tipo de objeto
	@RequestMapping(value="/{id}" , method = RequestMethod.GET)
	public ResponseEntity<?> buscar(@PathVariable("id") Long id){
		Livro livro = livroService.buscar(id);
		
		CacheControl cacheControl = CacheControl.maxAge(20, TimeUnit.SECONDS);
		
		return ResponseEntity.status(HttpStatus.OK).cacheControl(cacheControl).body(livro);
	}
	
	
	@RequestMapping(value="/{id}" , method = RequestMethod.DELETE)
	public ResponseEntity<Void> deletar(@PathVariable("id") Long id){
		livroService.deletar(id);	
		return ResponseEntity.noContent().build();
	}

	
	@RequestMapping(value="/{id}" , method = RequestMethod.PUT)
	public ResponseEntity<Void> atualizar(@RequestBody Livro livro, @PathVariable("id") Long id){
		
		livro.setId(id);
		livroService.atualizar(livro);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}/comentarios" , method = RequestMethod.POST)
	public ResponseEntity<Void> adicionarComentario(@PathVariable("id")Long livroID,
			                                        @RequestBody Comentario comentario){
		
		//Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		//comentario.setUsuario(auth.getName());
		
		livroService.salvaComentario(livroID, comentario);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
		return ResponseEntity.created(uri).build();
		
	}
	
	@RequestMapping(value="/{id}/comentarios" , method = RequestMethod.GET)
	public ResponseEntity<List<Comentario>> listarComentarios(@PathVariable("id") Long livroID){
		
		List<Comentario> comentarios = livroService.listarComentarios(livroID);
		
		return ResponseEntity.status(HttpStatus.OK).body(comentarios);
	}
	
}














