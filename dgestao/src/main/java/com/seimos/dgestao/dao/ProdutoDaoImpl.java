package com.seimos.dgestao.dao;

import org.springframework.stereotype.Repository;

import com.seimos.commons.dao.GenericDaoImpl;

import com.seimos.dgestao.dao.ProdutoDao;
import com.seimos.dgestao.domain.Produto;

/**
 * @author Moesio Medeiros
 * @date Fri Oct 24 18:21:48 BRT 2014
 */
@Repository
public class ProdutoDaoImpl extends GenericDaoImpl<Produto> implements ProdutoDao {
}