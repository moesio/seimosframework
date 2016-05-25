package com.seimos.programa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author moesio @ gmail.com
 * @date May 24, 2016 8:56:29 PM
 */
@Entity
@Table
public class Pessoa {

	@Id
	@GeneratedValue
	@Column(length = 5)
	private Integer id;
	@Column(length = 100, nullable = false)
	private String nome;
	private Boolean varao;
	private Boolean batizado;
	private Boolean leitor;
	private Boolean dianteira;
	private Boolean apoio;
	private Boolean ativo;

	public Integer getId() {
		return id;
	}

	public Pessoa setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getNome() {
		return nome;
	}

	public Pessoa setNome(String nome) {
		this.nome = nome;
		return this;
	}

	public Boolean getVarao() {
		return varao;
	}

	public Pessoa setVarao(Boolean varao) {
		this.varao = varao;
		return this;
	}

	public Boolean getBatizado() {
		return batizado;
	}

	public Pessoa setBatizado(Boolean batizado) {
		this.batizado = batizado;
		return this;
	}

	public Boolean getLeitor() {
		return leitor;
	}

	public Pessoa setLeitor(Boolean leitor) {
		this.leitor = leitor;
		return this;
	}

	public Boolean getDianteira() {
		return dianteira;
	}

	public Pessoa setDianteira(Boolean dianteira) {
		this.dianteira = dianteira;
		return this;
	}

	public Boolean getApoio() {
		return apoio;
	}

	public Pessoa setApoio(Boolean apoio) {
		this.apoio = apoio;
		return this;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public Pessoa setAtivo(Boolean ativo) {
		this.ativo = ativo;
		return this;
	}

}
