package com.generation.farmaciaonline.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.generation.farmaciaonline.model.Produto;


@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	
	//select * from tb_produtos name;
	public List<Produto> findAllByNomeContainingIgnoreCase(@Param("nome")String nome);
	
	//select * from tb_produtos where preco>preco order by preco;
	public List<Produto>findByPrecoGreaterThanOrderByPreco(@Param("preco")BigDecimal preco);
	
	//select * from tb_produtos where preco<preco order by preco desc;
	public List<Produto> findByPrecoLessThanOrderByPrecoDesc(@Param("preco")BigDecimal preco);
	
	//select * from tb_produtos where preco between precoMin and precoMax;
	public List<Produto>findAllByPrecoBetween(BigDecimal precoMin, BigDecimal precoMax);
	
	//select name, laboratorio from tb_produtos
	public List<Produto>findAllByNomeContainingAndLaboratorioContainingIgnoreCase
	(@Param("nome") String nome, @Param("laboratorio") String laboratorio);
	
	

}
