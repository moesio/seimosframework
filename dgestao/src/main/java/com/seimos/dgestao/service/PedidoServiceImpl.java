package com.seimos.dgestao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seimos.commons.service.GenericServiceImpl;

import com.seimos.dgestao.dao.PedidoDao;
import com.seimos.dgestao.service.PedidoService;
import com.seimos.dgestao.domain.Pedido;

/**
 * @author Moesio Medeiros
 * @date Fri Oct 24 18:21:49 BRT 2014
 */
@Service
public class PedidoServiceImpl extends GenericServiceImpl<Pedido, PedidoDao> implements PedidoService {

	private PedidoDao pedidoDao;
	
	@Autowired
	private void setPedidoDao(PedidoDao pedidoDao) {
		this.pedidoDao = pedidoDao;
	}

	@Override
	public PedidoDao getDao() {
		return pedidoDao;
	}

}