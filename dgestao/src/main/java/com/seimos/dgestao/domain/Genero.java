package com.seimos.dgestao.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author moesio @ gmail.com
 * @date Oct 22, 2014 12:51:47 PM
 */
@Table
@Entity
public class Genero {
	@Id
	@GeneratedValue
	@Column(length = 5)
	private Integer id;
	@Column(length = 50, nullable = false)
	private String nome;
	
	public Integer getId() {
		return id;
	}

	public Genero setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getNome() {
		return nome;
	}

	public Genero setNome(String name) {
		this.nome = name;
		return this;
	}

	@Override
	public String toString() {
		return "Genero [id=" + id + ", nome=" + nome + "]";
	}


//	public static class Builder {
//		private Integer id;
//		private String nome;
//
//		public Builder id(Integer id) {
//			this.id = id;
//			return this;
//		}
//
//		public Builder nome(String nome) {
//			this.nome = nome;
//			return this;
//		}
//
//		public Genero build() {
//			return new Genero(this);
//		}
//	}
//
//	private Genero(Builder builder) {
//		this.id = builder.id;
//		this.nome = builder.nome;
//	}
}
