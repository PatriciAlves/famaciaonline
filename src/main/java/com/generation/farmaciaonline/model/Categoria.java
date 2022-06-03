package com.generation.farmaciaonline.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;


@Data
@Entity
@Table(name = "tb_categorias")
public class Categoria {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "Informe o nome da categoria")
	private String nome;
	
	@NotBlank(message = "Informe uma descrição válida")
	@Size(min = 15, max = 500)
	private String descricao;
	
	@OneToMany(mappedBy = "categoria",cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties
	private List<Produto> produto;

}
