package com.generation.farmaciaonline.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_produtos")
public class Produto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "Informe o nome do produto.")
	private String nome;
	
	@NotBlank(message = "Informe uma descrição válida.")
	@Size(min = 15, max = 500)
	private String descricao;
	
	@NotNull(message = "Informe o laboratório")
	private String laboratorio;
	
	@NotNull(message = "A foto é obrigatória")
	private String foto;
	
	@NotNull(message = "Informe a quantidade.")
	private int qtd;
	
	@NotNull(message ="Informe a data de vencimento do produto")
	@Column(name ="data_validade")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataValidade;
	
	@NotNull(message = "Informe o preço.")
	private BigDecimal preco;
	
	@ManyToOne()
	@JsonIgnoreProperties("produto")
	private Categoria categoria;

}
