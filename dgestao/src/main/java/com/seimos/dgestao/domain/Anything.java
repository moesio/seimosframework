package com.seimos.dgestao.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author moesio @ gmail.com
 * @date May 2, 2016 6:41:45 PM
 */
@Entity
@Table
public class Anything {

	@Id
	@GeneratedValue
	private Integer id;
	@Column(length = 250, unique = true)
	private String nome;

	//	@OneToMany
	//	private List<Attribute> attributes;

	public Integer getId() {
		return id;
	}

	public Anything setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getNome() {
		return nome;
	}

	public Anything setNome(String nome) {
		this.nome = nome;
		return this;
	}
	//	public List<Attribute> getAttributes() {
	//		return attributes;
	//	}
	//
	//	public Anything setAttributes(List<Attribute> attributes) {
	//		this.attributes = attributes;
	//		return this;
	//	}

}
