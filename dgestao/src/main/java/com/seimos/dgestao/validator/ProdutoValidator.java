package com.seimos.dgestao.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.seimos.commons.validator.GenericValidator;
import com.seimos.dgestao.domain.Produto;

/**
 * @author moesio @ gmail.com
 * @date Apr 5, 2016 4:16:08 PM
 */
@Component
public class ProdutoValidator extends GenericValidator<Produto> {
	/* (non-Javadoc)
	 * @see com.seimos.commons.validator.GenericValidator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object target, Errors errors) {

//		errors.reject("alguma.chave", "Algum erro padrão");
//		errors.reject("app.name");
//		errors.reject("mais.error", "mais erros");
		
		super.validate(target, errors);
	}
}
