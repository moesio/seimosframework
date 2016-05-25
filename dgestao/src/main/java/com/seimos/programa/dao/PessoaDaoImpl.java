package com.seimos.programa.dao;

import org.springframework.stereotype.Repository;

import com.seimos.commons.dao.GenericDaoImpl;
import com.seimos.programa.domain.Pessoa;

/**
 * @author moesio @ gmail.com
 * @date May 24, 2016 9:03:01 PM
 */
@Repository
public class PessoaDaoImpl extends GenericDaoImpl<Pessoa> implements PessoaDao {

}
