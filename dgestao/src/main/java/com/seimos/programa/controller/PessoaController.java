package com.seimos.programa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.seimos.commons.controller.GenericCrudController;
import com.seimos.commons.service.GenericService;
import com.seimos.commons.validator.GenericValidator;
import com.seimos.programa.domain.Pessoa;
import com.seimos.programa.service.PessoaService;
import com.seimos.programa.validator.PessoaValidator;

/**
 * @author moesio @ gmail.com
 * @date May 24, 2016 9:06:37 PM
 */
@Controller
@RequestMapping("/pessoa")
public class PessoaController extends GenericCrudController<Pessoa> {

	private PessoaService pessoaService;
	private PessoaValidator pessoaValidator;

	@Override
	public GenericService<Pessoa> getService() {
		return pessoaService;
	}

	@Override
	public GenericValidator<Pessoa> getValidator() {
		return pessoaValidator;
	}

	@Autowired
	public void setPessoaService(PessoaService pessoaService) {
		this.pessoaService = pessoaService;
	}

	@Autowired
	public void setPessoaValidator(PessoaValidator pessoaValidator) {
		this.pessoaValidator = pessoaValidator;
	}

}
