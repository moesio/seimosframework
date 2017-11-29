package com.seimos.commons.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.seimos.commons.hibernate.Filter;
import com.seimos.commons.hibernate.Filters;
import com.seimos.commons.web.formbuilder.SelectOption;


public interface GenericService<Domain>
{
	Domain create(Domain entity);
	void createOrUpdate(Domain entity);
	Domain retrieve(Integer id);
	Domain update(Domain entity);
	void remove(Serializable id) throws InstantiationException, IllegalAccessException;
	void remove(Domain entity);
	List<Domain> list();
	ArrayList<SelectOption> tinyList();
    List<Domain> sortedFind(Domain entity, String... order);
    List<Domain> find(Domain entity);
    List<Domain> find(Domain entity, Integer firstResult, Integer maxResult);
	List<Domain> find(Filters filters);
	List<Domain> find(Filters filters, Integer firstResult, Integer maxResult);
	List<Domain> find(List<Filter> filters);
	List<Domain> find(List<Filter> filters, Integer firstResult, Integer maxResult);
	List<Domain> find(Filter... filters);
	Domain findUnique(Domain entity);
	Domain findUnique(Filters filters);
	Domain findUnique(Filter...filters);
	Domain findUnique(List<Filter> filters);
	Domain findById(Object id);
}
