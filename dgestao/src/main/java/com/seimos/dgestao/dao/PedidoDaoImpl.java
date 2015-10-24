package com.seimos.dgestao.dao;

import org.springframework.stereotype.Repository;

import com.seimos.commons.dao.GenericDaoImpl;

import com.seimos.dgestao.dao.PedidoDao;
import com.seimos.dgestao.domain.Pedido;

/**
 * @author Moesio Medeiros
 * @date Fri Oct 24 18:21:49 BRT 2014
 */
@Repository
public class PedidoDaoImpl extends GenericDaoImpl<Pedido> implements PedidoDao {
}