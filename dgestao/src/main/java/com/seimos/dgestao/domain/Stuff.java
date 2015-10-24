package com.seimos.dgestao.domain;

/**
 * @author moesio @ gmail.com
 * @date Oct 20, 2014 12:47:53 AM
 */
public class Stuff {
	private Integer id;
	private String name;

	public Integer getId() {
		return id;
	}

	public Stuff setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public Stuff setName(String name) {
		this.name = name;
		return this;
	}
}
