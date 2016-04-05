package com.seimos.dgestao.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Moesio Medeiros
 * @date Fri Oct 24 12:43:36 BRT 2014
 */
@Entity
@Table
public class Produto {
	@Id
	@GeneratedValue
	private Integer id;
	@ManyToOne
	@JoinColumn(nullable = false)
	private Genero genero;
	@Column(nullable = false, length = 5)
	private String codigo;
	@Column(nullable = false, length = 50)
	private String nome;
	@Column(nullable = false, length = 3)
	private Integer validadeAposAberto;
	@Column(nullable = true, length = 5 * 1024)
	private String imagem;

	public Integer getId() {
		return this.id;
	}

	public Produto setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getCodigo() {
		return codigo;
	}

	public Produto setCodigo(String codigo) {
		this.codigo = codigo;
		return this;
	}

	public String getNome() {
		return nome;
	}

	public Produto setNome(String nome) {
		this.nome = nome;
		return this;
	}

	public Genero getGenero() {
		return genero;
	}

	public Produto setGenero(Genero genero) {
		this.genero = genero;
		return this;
	}

	public Integer getValidadeAposAberto() {
		return validadeAposAberto;
	}

	public Produto setValidadeAposAberto(Integer validadeAposAberto) {
		this.validadeAposAberto = validadeAposAberto;
		return this;
	}

	public String getImagem() {
		return imagem;
	}

	public Produto setImagem(String imagem) {
		this.imagem = imagem;
		return this;
	}

	@Override
	public String toString() {
		return "Produto [genero=" + genero + ", codigo=" + codigo + ", nome=" + nome + "]";
	}
}