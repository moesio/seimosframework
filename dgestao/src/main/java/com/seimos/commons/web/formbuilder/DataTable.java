package com.seimos.commons.web.formbuilder;

import java.util.List;

/**
 * Encapsulates ajax to grid DataTable
 * 
 * @author moesio @ gmail.com
 * @date May 14, 2016 2:06:55 PM
 */
public class DataTable<Entity> {
	private Integer draw;
	private Integer recordsTotal;
	private Integer recordsFiltered;
	private List<Entity> data;

	public Integer getDraw() {
		return draw;
	}

	public DataTable<Entity> setDraw(Integer draw) {
		this.draw = draw;
		return this;
	}

	public Integer getRecordsTotal() {
		return recordsTotal;
	}

	public DataTable<Entity> setRecordsTotal(Integer recordsTotal) {
		this.recordsTotal = recordsTotal;
		return this;
	}

	public Integer getRecordsFiltered() {
		return recordsFiltered;
	}

	public DataTable<Entity> setRecordsFiltered(Integer recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
		return this;
	}

	public List<Entity> getData() {
		return data;
	}

	public DataTable<Entity> setData(List<Entity> data) {
		this.data = data;
		return this;
	}

}
