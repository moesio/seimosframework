package com.seimos.dgestao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.seimos.commons.controller.GenericCrudController;
import com.seimos.commons.service.GenericService;
import com.seimos.commons.validator.GenericValidator;
import com.seimos.dgestao.domain.Fornecedor;
import com.seimos.dgestao.service.FornecedorService;
import com.seimos.dgestao.validator.FornecedorValidator;

/**
 * This controller will response a call about module of Fornecedor type. It's will
 * controller operations by retrieve, create, update and remove.
 * 
 * @author Moesio Medeiros
 * @date Fri Oct 24 18:21:48 BRT 2014
 * 
 */
@Controller
@RequestMapping(value = "/fornecedor")
public class FornecedorController extends GenericCrudController<Fornecedor> {

	private FornecedorService fornecedorService;
	private FornecedorValidator fornecedorValidator;

	@Autowired
	public void setFornecedorService (FornecedorService fornecedorService) {
		this.fornecedorService = fornecedorService;
	}

	@Autowired
	public void setFornecedorValidator(FornecedorValidator fornecedorValidator) {
		this.fornecedorValidator = fornecedorValidator;
	}
	
	@Override
	public GenericService<Fornecedor> getService() {
		return fornecedorService;
	}

	@Override
	public GenericValidator<Fornecedor> getValidator() {
		return fornecedorValidator;
	}

}
