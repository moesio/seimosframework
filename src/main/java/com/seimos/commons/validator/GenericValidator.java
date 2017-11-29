package com.seimos.commons.validator;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.seimos.commons.web.formbuilder.FormField;
import com.seimos.commons.web.formbuilder.Page;

/**
 * @author moesio @ gmail.com
 * @date Apr 5, 2016 1:44:32 PM
 */
@Component
public class GenericValidator<Entity> implements Validator {

	protected static final Logger logger = LoggerFactory.getLogger(GenericValidator.class);
	protected ReloadableResourceBundleMessageSource messageSource;

	@Autowired
	public void setMessageSource(ReloadableResourceBundleMessageSource messageSource) {
		this.messageSource = messageSource;
	}

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	public boolean supports(Class<?> clazz) {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	public void validate(Object target, Errors errors) {
		List<FormField> formFields = new Page(target.getClass()).getFormFields();

		for (FormField formField : formFields) {
			String fieldName = formField.getName().substring(formField.getName().indexOf('.') + 1);
			
			Object fieldValue = errors.getFieldValue(fieldName);
			if (formField.getMandatory()) {
				String label = messageSource.getMessage(formField.getLabel(), null, null, null);
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, fieldName, "error.required", new String[] { label });
			}

			Integer formFieldLength = formField.getLength();
			if (formFieldLength != null) {
				if (fieldValue != null) {
					if (fieldValue.toString().length() > formFieldLength) {
						errors.rejectValue(fieldName, "error.length");
					}
				}
			}
		}
	}
}
