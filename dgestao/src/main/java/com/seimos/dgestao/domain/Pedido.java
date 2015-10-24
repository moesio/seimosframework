package com.seimos.dgestao.domain;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Moesio Medeiros
 * @date Fri Oct 24 13:48:46 BRT 2014
 */
@Entity
@Table
public class Pedido
{
	@Id
    @GeneratedValue
	private Integer id;
	private Calendar data;
	@ManyToOne
	private Fornecedor fornecedor;
	@OneToMany
	private List<Produto> produtos;
	
	public Integer getId()
	{
		return this.id;
	}
	
	public void setId(Integer id)
	{
		this.id = id;
	}

	public Calendar getData() {
		return data;
	}

	public Pedido setData(Calendar data) {
		this.data = data;
		return this;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public Pedido setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
		return this;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public Pedido setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
		return this;
	}

}