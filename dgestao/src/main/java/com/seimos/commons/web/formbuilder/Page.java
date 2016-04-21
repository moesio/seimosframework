package com.seimos.commons.web.formbuilder;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;

import org.springframework.util.StringUtils;

import com.seimos.commons.reflection.Reflection;

/**
 * @author moesio @ gmail.com
 * @date Sep 28, 2014 12:48:24 PM
 */
public class Page implements Serializable {

	private String title;
	private List<FormField> formFields;
	private Object data;
	private HashMap<String, Object> properties = new HashMap<String, Object>();
	private String entityName;

	@SuppressWarnings("unused")
	private Page() {
	}

	// TODO Create a webForm cache to avoid reflection for every request 
	public Page(Class<?> clazz) {
		entityName = StringUtils.uncapitalize(clazz.getSimpleName());
		title = new StringBuilder(entityName).append(".page.title").toString();
		formFields = new ArrayList<FormField>();
		formFields.addAll(extractFields(clazz));
	}

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

	public String getEntityName() {
		return entityName;
	}

	public Page setEntityName(String root) {
		this.entityName = root;
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

	public HashMap<String, Object> getProperties() {
		return properties;
	}

	public Page setProperties(HashMap<String, Object> properties) {
		this.properties = properties;
		return this;
	}

	public void addProperty(String key, Object value) {
		properties.put(key, value);
	}

	@Override
	public String toString() {
		return "Page [title=" + title + ", formFields=" + formFields + ", data=" + data + ", properties=" + properties + ", root=" + entityName + "]";
	}

}
