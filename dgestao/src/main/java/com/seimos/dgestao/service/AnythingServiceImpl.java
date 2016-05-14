package com.seimos.dgestao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seimos.commons.dao.GenericDao;
import com.seimos.commons.service.GenericServiceImpl;
import com.seimos.dgestao.dao.AnythingDao;
import com.seimos.dgestao.domain.Anything;

/**
 * @author moesio @ gmail.com
 * @date May 2, 2016 9:12:10 PM
 */
@Service
public class AnythingServiceImpl extends GenericServiceImpl<Anything, AnythingDao> implements AnythingService {

	private AnythingDao anythingDao;
	
	@Autowired
	public void setAnythingDao(AnythingDao anythingDao) {
		this.anythingDao = anythingDao;
	}
	
	/* (non-Javadoc)
	 * @see com.seimos.commons.service.GenericServiceImpl#getDao()
	 */
	@Override
	public GenericDao<Anything> getDao() {
		return anythingDao;
	}

}
