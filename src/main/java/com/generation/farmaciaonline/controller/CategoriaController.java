package com.generation.farmaciaonline.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.farmaciaonline.model.Categoria;
import com.generation.farmaciaonline.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoriaController {

	@Autowired
	private CategoriaRepository cat;

	// Busca todos produtos - lista
	@GetMapping
	public ResponseEntity<List<Categoria>> getAll() {
		return ResponseEntity.ok(cat.findAll());
	}

	// Busca por nome
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Categoria>> getByNome(@Valid @PathVariable String nome) {
		return ResponseEntity.ok(cat.findAllByNomeContainingIgnoreCase(nome));

	}

	// Busca por id
	@GetMapping("/{id}")
	public ResponseEntity<Categoria> getById(@PathVariable Long id) {
		return cat.findById(id).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

	}

	// Cria oprodutos
	@PostMapping
	public ResponseEntity<Categoria> postCategoria(@Valid @RequestBody Categoria categoria) {
		return ResponseEntity.status(HttpStatus.CREATED).body(cat.save(categoria));
	}

	// Atualiza produtos
	@PutMapping
	ResponseEntity<Categoria> putCategoria(@Valid @RequestBody Categoria categoria) {
			return cat.findById(categoria.getId())
					.map(resp -> ResponseEntity.status(HttpStatus.OK).body(cat.save(categoria)))
					.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	
	}

	// Deleta produto por id
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCategoria(@PathVariable Long id) {
		return cat.findById(id).map(resp -> {
			cat.deleteById(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

		}).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

}
