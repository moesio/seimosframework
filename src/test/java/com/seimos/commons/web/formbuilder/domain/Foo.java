package com.seimos.commons.web.formbuilder.domain;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * @author moesio
 * @date 2018-01-09 15:16:50
 *
 */
public class Foo {
	private Boolean bool;

	private LocalDate date;

	@OneToMany
	private List<Some> many;

	private Integer integer;

	private Double doub;

	private Float flo;

	private Any any;

	@Id
	private Integer idFoo;

	@ManyToOne
	private Some some;

	@OneToOne
	private Some one;

	@Embedded
	private Within within;

	public Boolean getBool() {
		return bool;
	}

	public void setBool(Boolean bool) {
		this.bool = bool;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public List<Some> getMany() {
		return many;
	}

	public void setMany(List<Some> many) {
		this.many = many;
	}

	public Integer getInteger() {
		return integer;
	}

	public void setInteger(Integer integer) {
		this.integer = integer;
	}

	public Double getDoub() {
		return doub;
	}

	public void setDoub(Double doub) {
		this.doub = doub;
	}

	public Float getFlo() {
		return flo;
	}

	public void setFlo(Float flo) {
		this.flo = flo;
	}

	public Any getAny() {
		return any;
	}

	public void setAny(Any any) {
		this.any = any;
	}

	public Integer getIdFoo() {
		return idFoo;
	}

	public void setId(Integer idFoo) {
		this.idFoo = idFoo;
	}

	public Some getSome() {
		return some;
	}

	public void setSome(Some some) {
		this.some = some;
	}

	public Some getOne() {
		return one;
	}

	public void setOne(Some one) {
		this.one = one;
	}

	public Within getWithin() {
		return within;
	}

	public void setWithin(Within within) {
		this.within = within;
	}

}