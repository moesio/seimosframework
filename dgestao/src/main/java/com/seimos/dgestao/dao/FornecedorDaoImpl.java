package com.seimos.dgestao.dao;

import org.springframework.stereotype.Repository;

import com.seimos.commons.dao.GenericDaoImpl;

import com.seimos.dgestao.dao.FornecedorDao;
import com.seimos.dgestao.domain.Fornecedor;

/**
 * @author Moesio Medeiros
 * @date Fri Oct 24 18:21:48 BRT 2014
 */
@Repository
public class FornecedorDaoImpl extends GenericDaoImpl<Fornecedor> implements FornecedorDao {
}