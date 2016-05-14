package com.seimos.dgestao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.seimos.commons.controller.GenericCrudController;
import com.seimos.commons.service.GenericService;
import com.seimos.commons.validator.GenericValidator;
import com.seimos.dgestao.domain.Anything;
import com.seimos.dgestao.service.AnythingService;
import com.seimos.dgestao.validator.AnythingValidator;

/**
 * @author moesio @ gmail.com
 * @date May 2, 2016 9:14:02 PM
 */
@Controller
@RequestMapping("/anything")
public class AnythingController extends GenericCrudController<Anything> {

	private AnythingService anythingService;
	private AnythingValidator anythingValidator;

	@Autowired
	public void setAnythingService(AnythingService anythingService) {
		this.anythingService = anythingService;
	}
	
	@Autowired
	public void setAnythingValidator(AnythingValidator anythingValidator) {
		this.anythingValidator = anythingValidator;
	}
	
	@Override
	public GenericService<Anything> getService() {
		return anythingService;
	}

	@Override
	public GenericValidator<Anything> getValidator() {
		return anythingValidator;
	}

}
