package com.seimos.commons.web.formbuilder;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;

import com.seimos.commons.reflection.Reflection;

/**
 * @author moesio @ gmail.com
 * @date Sep 28, 2014 12:48:24 PM
 */
public class Page implements Serializable {

	private String title;
	private List<FormField> formFields;
	private Object data;

	@SuppressWarnings("unused")
	private Page() {
	}

	// TODO Create a webForm cache to avoid reflection for every request 
	public Page(Class<?> clazz) {
		title = new StringBuilder(clazz.getSimpleName().toLowerCase()).append(".page.title").toString();
		formFields = new ArrayList<FormField>();
		formFields.addAll(extractFields(clazz));
	}

	/**
	 * @param clazz
	 * @return
	 */
	private Collection<? extends FormField> extractFields(Class<?> clazz) {
		return extractFields("", clazz);
	}

	private Collection<? extends FormField> extractFields(String prefix, Class<?> clazz) {
		ArrayList<FormField> formFields = new ArrayList<FormField>();
		Field[] noTransientFields = Reflection.getNoTransientFields(clazz);
		for (Field field : noTransientFields) {
			FormField formField = new FormField(prefix, clazz, field);
			if (field.isAnnotationPresent(Embedded.class) || field.isAnnotationPresent(EmbeddedId.class)) {
				formFields.addAll(extractFields(clazz.getSimpleName(), field.getType()));
			} else {
				formFields.add(formField);
			}
		}
		return formFields;
	}

	public String getTitle() {
		return title;
	}

	public Page setTitle(String title) {
		this.title = title;
		return this;
	}

	public List<FormField> getFormFields() {
		return formFields;
	}

	public Page setFormFields(List<FormField> formFields) {
		this.formFields = formFields;
		return this;
	}

	public Object getData() {
		return data;
	}

	public Page setData(Object data) {
		this.data = data;
		return this;
	}
}
