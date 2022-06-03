package com.generation.farmaciaonline.controller;

import java.math.BigDecimal;
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

import com.generation.farmaciaonline.model.Produto;
import com.generation.farmaciaonline.repository.CategoriaRepository;
import com.generation.farmaciaonline.repository.ProdutoRepository;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutoController {

	@Autowired
	private ProdutoRepository prod;

	@Autowired
	private CategoriaRepository cat;

	// Busca todos produtos - lista
	@GetMapping
	public ResponseEntity<List<Produto>> getAll() {
		return ResponseEntity.ok(prod.findAll());
	}

	// Busca por nome
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Produto>> getByNome(@Valid @PathVariable String nome) {
		return ResponseEntity.ok(prod.findAllByNomeContainingIgnoreCase(nome));

	}

	// Busca por preco maior que
	@GetMapping("/precomaior/{preco}")
	public ResponseEntity<List<Produto>> getByPrecoMaior(@Valid @PathVariable BigDecimal preco) {
		return ResponseEntity.ok(prod.findByPrecoGreaterThanOrderByPreco(preco));

	}

	// Busca por preco menor que
	@GetMapping("/precomenor/{preco}")
	public ResponseEntity<List<Produto>> getByPrecoMenor(@Valid @PathVariable BigDecimal preco) {
		return ResponseEntity.ok(prod.findByPrecoLessThanOrderByPrecoDesc(preco));

	}

	// Busca entre precos
	@GetMapping("/preco/de{precoMin}a{precoMax}")
	public ResponseEntity<List<Produto>> getByPrecoBet(@PathVariable BigDecimal precoMin,
			@PathVariable BigDecimal precoMax) {
		return ResponseEntity.ok(prod.findAllByPrecoBetween(precoMin, precoMax));

	}

	// Busca por nome e laboratorio
	@GetMapping("/{nome}/{laboratorio}")
	public ResponseEntity<List<Produto>> getByNomeLab(@PathVariable String nome,@PathVariable String laboratorio) {
		return ResponseEntity.ok(prod.findAllByNomeContainingAndLaboratorioContainingIgnoreCase(nome, laboratorio));

	}

	// Busca por id
	@GetMapping("/{id}")
	public ResponseEntity<Produto> getById(@PathVariable Long id) {
		return prod.findById(id).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

	}

	// Cria oprodutos
	@PostMapping
	public ResponseEntity<Produto> postProduto(@Valid @RequestBody Produto produto) {
		return cat.findById(produto.getCategoria().getId())
				.map(resp -> ResponseEntity.status(HttpStatus.CREATED).body(prod.save(produto)))
				.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	}

	// Atualiza produtos
	@PutMapping
	ResponseEntity<Produto> putProduto(@Valid @RequestBody Produto produto) {
		if (prod.existsById(produto.getId())) {
			return cat.findById(produto.getCategoria().getId())
					.map(resp -> ResponseEntity.status(HttpStatus.OK).body(prod.save(produto)))
					.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	// Deleta produto por id
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProduto(@PathVariable Long id) {
		return prod.findById(id).map(resp -> {
			prod.deleteById(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

		}).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

}
