package com.seimos.dgestao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seimos.commons.service.GenericServiceImpl;

import com.seimos.dgestao.dao.FornecedorDao;
import com.seimos.dgestao.service.FornecedorService;
import com.seimos.dgestao.domain.Fornecedor;

/**
 * @author Moesio Medeiros
 * @date Fri Oct 24 18:21:48 BRT 2014
 */
@Service
public class FornecedorServiceImpl extends GenericServiceImpl<Fornecedor, FornecedorDao> implements FornecedorService {

	private FornecedorDao fornecedorDao;
	
	@Autowired
	private void setFornecedorDao(FornecedorDao fornecedorDao) {
		this.fornecedorDao = fornecedorDao;
	}

	@Override
	public FornecedorDao getDao() {
		return fornecedorDao;
	}

}