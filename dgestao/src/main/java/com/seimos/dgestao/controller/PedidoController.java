package com.seimos.dgestao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.seimos.commons.controller.GenericCrudController;
import com.seimos.commons.service.GenericService;
import com.seimos.dgestao.domain.Pedido;
import com.seimos.dgestao.service.PedidoService;

/**
 * This controller will response a call about module of Pedido type. It's will
 * controller operations by retrieve, create, update and remove.
 * 
 * @author Moesio Medeiros
 * @date Fri Oct 24 18:21:49 BRT 2014
 * 
 */
@Controller
@RequestMapping(value = "/pedido")
public class PedidoController extends GenericCrudController<Pedido> {

	private PedidoService pedidoService;

	@Autowired
	public void setPedidoService (PedidoService pedidoService) {
		this.pedidoService = pedidoService;
	}
	
	@Override
	public GenericService<Pedido> getService() {
		return pedidoService;
	}

}
