package com.seimos.commons.web.formbuilder;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.MappedSuperclass;

import org.springframework.util.StringUtils;

import com.seimos.commons.reflection.Reflection;

/**
 * @author moesio @ gmail.com
 * @date Sep 28, 2014 12:48:24 PM
 */
public class Page implements Serializable {

	private static final long serialVersionUID = -3944698356746138568L;

	private String title;

	private LinkedHashMap<String, FormField> formFields = new LinkedHashMap<String, FormField>();

	private Object data;

	private HashMap<String, Object> properties = new HashMap<String, Object>();

	private String entityName;

	private Class<?> entityClass;

	// TODO Create a webForm cache to avoid reflection for every request 
	public Page(Class<?> root) {
		entityName = StringUtils.uncapitalize(root.getSimpleName());
		entityClass = root;
		title = new StringBuilder(entityName).append(".page.title").toString();
		extractFormFields(root, "");
	}

	private void extractFormFields(Class<?> entity, String path) {
		List<Field> fields = Reflection.getNoTransientFields(entity);
		if (entity.getSuperclass() != Object.class
				&& entity.getSuperclass().isAnnotationPresent(MappedSuperclass.class)) {
			extractFormFields(entity.getSuperclass(), path);
		}
		for (Field field : fields) {
			if (Reflection.isEmbedded(field.getType())) {
				extractFormFields(field.getType(), path.concat(".").concat(field.getName()));
				//				if (field.getType().getSuperclass() != Object.class) {
				//					extractFormFields(field.getType().getSuperclass(), path.concat(".").concat(field.getName()));
				//				}
			} else {
				String fullPath = path.concat(".").concat(field.getName());
				FormField formField = new FormField(entityClass, fullPath.substring(1));
				formFields.put(formField.getName(), formField);
			}
		}
	}

	public String getTitle() {
		return title;
	}

	public String getEntityName() {
		return entityName;
	}

	public LinkedHashMap<String, FormField> getFormFields() {
		return formFields;
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
		return "Page [title=" + title + ", formFields=" + formFields + ", data=" + data + ", properties=" + properties
				+ ", root=" + entityName + "]";
	}

}
