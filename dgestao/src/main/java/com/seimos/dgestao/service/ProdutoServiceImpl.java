package com.seimos.dgestao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seimos.commons.service.GenericServiceImpl;

import com.seimos.dgestao.dao.ProdutoDao;
import com.seimos.dgestao.service.ProdutoService;
import com.seimos.dgestao.domain.Produto;

/**
 * @author Moesio Medeiros
 * @date Fri Oct 24 18:21:48 BRT 2014
 */
@Service
public class ProdutoServiceImpl extends GenericServiceImpl<Produto, ProdutoDao> implements ProdutoService {

	private ProdutoDao produtoDao;
	
	@Autowired
	private void setProdutoDao(ProdutoDao produtoDao) {
		this.produtoDao = produtoDao;
	}

	@Override
	public ProdutoDao getDao() {
		return produtoDao;
	}

}