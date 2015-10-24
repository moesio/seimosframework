package com.seimos.commons.service;

import java.io.Serializable;
import java.util.List;

import com.seimos.commons.dao.GenericDao;
import com.seimos.commons.hibernate.Filter;
import com.seimos.commons.hibernate.Filters;


public abstract class GenericServiceImpl<Domain, Dao extends GenericDao<Domain>> implements GenericService<Domain> {
	public abstract GenericDao<Domain> getDao();

	public Domain create(Domain entity) {
		return getDao().create(entity);
	}

	public void createOrUpdate(Domain entity) {
		getDao().createOrUpdate(entity);
	}

	public Domain retrieve(Integer id) {
		return getDao().retrieve(id);
	}

	public Domain update(Domain entity) {
		return getDao().update(entity);
	}

	public void remove(Serializable id) throws InstantiationException, IllegalAccessException{
		getDao().remove(id);
	}
	
	public void remove(Domain entity) {
		getDao().remove(entity);
	}

	public List<Domain> list() {
		return getDao().list();
	}

	public List<Domain> find(Filters filters) {
		return getDao().find(filters, null, null);
	}
	
	public List<Domain> find(Filters filters, Integer firstResult, Integer maxResult) {
		return getDao().find(filters, firstResult, maxResult);
	}
	
	public List<Domain> find(List<Filter> filters) {
		return getDao().find(filters, null, null);
	}

	public List<Domain> find(List<Filter> filters, Integer firstResult, Integer maxResult) {
		return getDao().find(filters, firstResult, maxResult);
	}
	
	public List<Domain> find(Filter... filters) {
		return getDao().find(filters);
	}

	public List<Domain> find(Domain entity) {
		return getDao().find(entity);
	}

	public List<Domain> sortedFind(Domain entity, String... order) {
		return getDao().sortedFind(entity, order);
	}
	
	public Domain findUnique(Filters filters){
		return getDao().findUnique(filters);
	}
	
	public Domain findUnique(Filter... filters)
	{
		return getDao().findUnique(filters);
	}

	public Domain findUnique(Domain entity) {
		return getDao().findUnique(entity);
	}
	
	public Domain findUnique(List<Filter> filters){
		return getDao().findUnique(filters);
	}

	public Domain findById(Object id) {
		return getDao().findById(id);
	}
}
