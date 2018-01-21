package com.seimos.commons.web.formbuilder.domain;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * @author moesio
 * @date 2018-01-09 15:16:43
 *
 */
@Embeddable
public class Within {
	private Boolean wbool;

	private LocalDate wdate;

	@OneToMany
	private List<Some> wmany;

	private Integer winteger;

	private Double wdoub;

	private Float wflo;

	private Any wany;

	@ManyToOne
	private Some wsome;

	@OneToOne
	private Some wone;

	@Embedded
	private Deep deep;

	public Boolean getWbool() {
		return wbool;
	}

	public void setWbool(Boolean wbool) {
		this.wbool = wbool;
	}

	public LocalDate getWdate() {
		return wdate;
	}

	public void setWdate(LocalDate wdate) {
		this.wdate = wdate;
	}

	public List<Some> getWmany() {
		return wmany;
	}

	public void setWmany(List<Some> wmany) {
		this.wmany = wmany;
	}

	public Integer getWinteger() {
		return winteger;
	}

	public void setWinteger(Integer winteger) {
		this.winteger = winteger;
	}

	public Double getWdoub() {
		return wdoub;
	}

	public void setWdoub(Double wdoub) {
		this.wdoub = wdoub;
	}

	public Float getWflo() {
		return wflo;
	}

	public void setWflo(Float wflo) {
		this.wflo = wflo;
	}

	public Any getWany() {
		return wany;
	}

	public void setWany(Any wany) {
		this.wany = wany;
	}

	public Some getWsome() {
		return wsome;
	}

	public void setWsome(Some wsome) {
		this.wsome = wsome;
	}

	public Some getWone() {
		return wone;
	}

	public void setWone(Some wone) {
		this.wone = wone;
	}
}