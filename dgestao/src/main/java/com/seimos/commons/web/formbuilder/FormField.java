package com.seimos.commons.web.formbuilder;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.util.StringUtils;

import com.seimos.commons.reflection.Reflection;

/**
 * @author moesio @ gmail.com
 * @date Sep 28, 2014 12:50:36 PM
 */
public class FormField implements Serializable {
	private T type;
	private String label;
	private String name;
	private Integer length;
	private Boolean nullable;

	public enum T {
		TEXT, INTEGER, SELECT, HIDDEN, BOOLEAN;

		private String reference;
		private List<?> list;
		private String idFieldName;

		public String getReference() {
			return reference;
		}

		public void setReference(String string) {
			this.reference = string;
		}

		public List<?> getList() {
			return list;
		}

		public void setList(List<?> list) {
			this.list = list;
		}
		
		public String getIdFieldName() {
			return idFieldName;
		}

		public void setIdFieldName(String idFieldName) {
			this.idFieldName = idFieldName;
		}
	}

	@SuppressWarnings("unused")
	private FormField() {
	}

	public FormField(String prefix, Class<?> clazz, Field field) {
		Column columnAnnotation = field.getAnnotation(Column.class);
		String className = StringUtils.uncapitalize(clazz.getSimpleName());
		StringBuilder prefixBuilded = new StringBuilder();
		if (!prefix.isEmpty()) {
			prefixBuilded.append(StringUtils.uncapitalize(prefix)).append(".");
		}
		prefixBuilded.append(className).append(".");
		if (columnAnnotation != null) {
			String nameInAnnotation = columnAnnotation.name();
			if (nameInAnnotation != null && !nameInAnnotation.isEmpty()) {
				name = prefixBuilded.append(nameInAnnotation).toString();
			} else {
				name = prefixBuilded.append(field.getName()).toString();
			}
			length = columnAnnotation.length();
			nullable = columnAnnotation.nullable();
		} else {
			name = prefixBuilded.append(field.getName()).toString();
			nullable = true;
		}
		label = new StringBuilder(className).append(".page.field.").append(field.getName()).append(".label").toString();

		Class<?> fieldType = field.getType();
		if (field.isAnnotationPresent(ManyToOne.class)) {
			type = T.SELECT;
			type.setReference(StringUtils.uncapitalize(field.getType().getSimpleName()));
			type.setIdFieldName(Reflection.getIdField(field.getType()).getName());
//			WebApplicationContext webContext = ContextLoader.getCurrentWebApplicationContext();
		} else if (field.isAnnotationPresent(Id.class)) {
			type = T.HIDDEN;
		} else {

			if (fieldType == String.class) {
				type = T.TEXT;
			}
			if (fieldType == Integer.class) {
				type = T.INTEGER;
			} else if (fieldType == Boolean.class) {
				type = T.BOOLEAN;
			} else {
				type = T.TEXT;
			}
		}
	}

	public T getType() {
		return type;
	}

	public FormField setType(T type) {
		this.type = type;
		return this;
	}

	public String getLabel() {
		return label;
	}

	public FormField setLabel(String label) {
		this.label = label;
		return this;
	}

	public String getName() {
		return name;
	}

	public FormField setName(String name) {
		this.name = name;
		return this;
	}

	public Integer getLength() {
		return length;
	}

	public FormField setLength(Integer size) {
		this.length = size;
		return this;
	}

	public Boolean getNullable() {
		return nullable;
	}

	public FormField setNullable(Boolean nullable) {
		this.nullable = nullable;
		return this;
	}

}
