package com.seimos.programa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seimos.commons.dao.GenericDao;
import com.seimos.commons.service.GenericServiceImpl;
import com.seimos.programa.dao.PessoaDao;
import com.seimos.programa.domain.Pessoa;

/**
 * @author moesio @ gmail.com
 * @date May 24, 2016 9:04:14 PM
 */
@Service
public class PessoaServiceImpl extends GenericServiceImpl<Pessoa, PessoaDao> implements PessoaService {

	private PessoaDao pessoaDao;

	@Override
	public GenericDao<Pessoa> getDao() {
		return pessoaDao;
	}

	@Autowired
	public void setPessoaDao(PessoaDao pessoaDao) {
		this.pessoaDao = pessoaDao;
	}

}