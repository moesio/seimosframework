package com.seimos.dgestao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.seimos.commons.controller.GenericCrudController;
import com.seimos.commons.service.GenericService;
import com.seimos.commons.validator.GenericValidator;
import com.seimos.dgestao.domain.Genero;
import com.seimos.dgestao.service.GeneroService;
import com.seimos.dgestao.validator.GeneroValidator;

/**
 * @author moesio @ gmail.com
 * @date Oct 22, 2014 6:57:40 PM
 */
@Controller
@RequestMapping("/genero")
public class GeneroController extends GenericCrudController<Genero> {

	private GeneroService generoService;
	private GeneroValidator generoValidator;

	@Override
	public GenericService<Genero> getService() {
		return generoService;
	}

	@Autowired
	public void setGeneroService(GeneroService generoService) {
		this.generoService = generoService;
	}

	@Override
	public GenericValidator<Genero> getValidator() {
		return generoValidator;
	}

	@Autowired
	public void setGeneroValidator(GeneroValidator generoValidator) {
		this.generoValidator = generoValidator;
	}

}
