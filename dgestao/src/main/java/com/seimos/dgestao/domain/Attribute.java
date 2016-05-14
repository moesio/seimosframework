package com.seimos.dgestao.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author moesio @ gmail.com
 * @date May 2, 2016 6:43:58 PM
 */
@Table
@Entity
public class Attribute {
	@Id
	@GeneratedValue
	private Integer id;
	@Column(length = 250)
	private String name;

	public Integer getId() {
		return id;
	}

	public Attribute setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public Attribute setName(String name) {
		this.name = name;
		return this;
	}

}
