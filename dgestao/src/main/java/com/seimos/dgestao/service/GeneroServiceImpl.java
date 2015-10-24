package com.seimos.dgestao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seimos.commons.dao.GenericDao;
import com.seimos.commons.service.GenericServiceImpl;
import com.seimos.dgestao.dao.GeneroDao;
import com.seimos.dgestao.domain.Genero;

/**
 * @author moesio @ gmail.com
 * @date Oct 22, 2014 6:55:15 PM
 */
@Service
public class GeneroServiceImpl extends GenericServiceImpl<Genero, GeneroDao> implements GeneroService {

	private GeneroDao generoDao;

	/**
	 * @param generoDao the generoDao to set
	 */
	@Autowired
	public void setGeneroDao(GeneroDao generoDao) {
		this.generoDao = generoDao;
	}

	/* (non-Javadoc)
	 * @see com.seimos.commons.service.GenericServiceImpl#getDao()
	 */
	@Override
	public GenericDao<Genero> getDao() {
		return generoDao;
	}
}
