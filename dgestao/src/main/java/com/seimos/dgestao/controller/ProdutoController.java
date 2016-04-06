package com.seimos.dgestao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.seimos.commons.controller.GenericCrudController;
import com.seimos.commons.service.GenericService;
import com.seimos.commons.validator.GenericValidator;
import com.seimos.dgestao.domain.Produto;
import com.seimos.dgestao.service.ProdutoService;
import com.seimos.dgestao.validator.ProdutoValidator;

/**
 * This controller will response a call about module of Produto type. It's will
 * controller operations by retrieve, create, update and remove.
 * 
 * @author Moesio Medeiros
 * @date Fri Oct 24 18:21:48 BRT 2014
 * 
 */
@Controller
@RequestMapping(value = "/produto")
public class ProdutoController extends GenericCrudController<Produto> {

	private ProdutoService produtoService;
	private ProdutoValidator produtoValidator;
	
	@Autowired
	public void setProdutoService (ProdutoService produtoService) {
		this.produtoService = produtoService;
	}

	@Autowired
	public void setProdutoValidator(ProdutoValidator produtoValidator) {
		this.produtoValidator = produtoValidator;
	}
	
	@Override
	public GenericService<Produto> getService() {
		return produtoService;
	}

	@Override
	public GenericValidator<Produto> getValidator() {
		return produtoValidator;
	}
	
}
