package com.seimos.commons.dao;

import java.io.Serializable;
import java.util.List;

import com.seimos.commons.hibernate.Filter;
import com.seimos.commons.hibernate.Filters;


/**
 * Generic class for commons CRUD operation encapsulation
 * 
 * @author Moesio Medeiros
 *
 * @param <Domain>
 */
public interface GenericDao<Domain>
{
	/**
	 * Retrieves current hibernate session
	 * 
	 * @return
	 */
//	Session getCurrentSession();
	
	/**
	 * Persists an entity on database
	 * 
	 * @param entity
	 * @return
	 */
	Domain create(Domain entity);
	
	/**
	 * Save or update based on id
	 * 
	 * @param entity
	 */
	void createOrUpdate(Domain entity);

	/**
	 * Retrieve an entity for ID
	 * 
	 * @param id
	 * @return
	 */
	Domain retrieve(Integer id);
	
	/**
	 * Updates an entity
	 * 
	 * @param entity
	 * @return
	 */
	Domain update(Domain entity);
	
	/**
	 * Deletes an entity based on id
	 * 
	 * @param id
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	void remove(Serializable id) throws InstantiationException, IllegalAccessException;
	
	/**
	 * Deletes an entity
	 * 
	 * @param entity
	 */
	void remove(Domain entity);
	
	/**
	 * List all available entries for model in database and its associations. 
	 * Caution! It can use huge memory
	 * 
	 * @return
	 */
	List<Domain> list();
	
	/**
	 * Find by example for entity using a list of string atributes for sorting
	 * 
	 * @param entity
	 * @param order
	 * @return
	 */
    List<Domain> sortedFind(Domain entity, String... order);

    /**
     * Find by example. All non null attributes will be used as restriction
     * 
     * @param entity
     * @return
     */
    List<Domain> find(Domain entity);
    
    /**
     * Find using filters as criteria
     * 
     * @see com.seimos.commons.hibernate.Filters
     * @param filters
     * @return
     */
	List<Domain> find(Filters filters);
	
	/**
	 * Find using filters as criteria, firstResult and maxResult. Usually used for paging
	 * 
     * @see com.seimos.commons.hibernate.Filters
     * @see find(Filters)
	 * @param filters
	 * @param firstResult
	 * @param maxResult
	 * @return
	 */
	List<Domain> find(Filters filters, Integer firstResult, Integer maxResult);
	
	/**
	 * Find using a list of Filter
	 * 
     * @see com.seimos.commons.hibernate.Filter
	 * @param filters
	 * @return
	 */
	List<Domain> find(List<Filter> filters);
	
	/**
	 * Find using a list of Filter, firstResult and maxResult. Usually used for paging
	 * 
     * @see com.seimos.commons.hibernate.Filter
	 * @param filters
	 * @param firstResult
	 * @param maxResult
	 * @return
	 */
	List<Domain> find(List<Filter> filters, Integer firstResult, Integer maxResult);
	
	/**
	 * Find using an array of Filter
	 * 
     * @see com.seimos.commons.hibernate.Filter
	 * @param filters
	 * @return
	 */
	List<Domain> find(Filter... filters);
	
	/**
     * Find by example. All non null attributes will be used as restriction
	 * 
	 * @param entity
	 * @return
	 */
	Domain findUnique(Domain entity);

	/**
     * Find using filters as criteria
     * 
     * @see com.seimos.commons.hibernate.Filters
	 * @param filters
	 * @return
	 */
	Domain findUnique(Filters filters);
	
	/**
	 * Find using an array of Filter
	 * 
     * @see com.seimos.commons.hibernate.Filter
	 * @param filters
	 * @return
	 */
	Domain findUnique(Filter...filters);
	
	/**
	 * Find using a list of Filter
	 * 
     * @see com.seimos.commons.hibernate.Filter
	 * @param filters
	 * @return
	 */
	Domain findUnique(List<Filter> filters);
	
	/**
	 * List using Filter("id", id) and Filter("descricao"). Too hardcoded for be maintained
	 * 
	 * @param id
	 * @return
	 */
	Domain findById(Object id);
}
