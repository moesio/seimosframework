package com.seimos.commons.web.formbuilder;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.util.StringUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.seimos.commons.reflection.Reflection;
import com.seimos.commons.service.GenericService;

/**
 * @author moesio @ gmail.com
 * @date Sep 28, 2014 12:50:36 PM
 */
public class FormField implements Serializable {
	private T type;
	private String label;
	private String name;
	private Integer length;
	private Boolean mandatory;
	private LinkedHashMap<String, String> populator;
	private String idFieldName;

	public enum T {
		TEXT, INTEGER, SELECT, HIDDEN, BOOLEAN, DOUBLE, DATE;

//		private String reference;
//		private LinkedHashMap<String, String> map;
//		private String idFieldName;
//
//		public String getReference() {
//			return reference;
//		}
//
//		public void setReference(String string) {
//			this.reference = string;
//		}
//
//		public String getIdFieldName() {
//			return idFieldName;
//		}
//
//		public void setIdFieldName(String idFieldName) {
//			this.idFieldName = idFieldName;
//		}
//
//		public LinkedHashMap<String, String> getMap() {
//			return map;
//		}
//
//		public void setMap(LinkedHashMap<String, String> map) {
//			this.map = map;
//		}
//
	}

	@SuppressWarnings("unused")
	private FormField() {
	}

	public FormField(String prefix, Class<?> clazz, Field field) {
		String className = StringUtils.uncapitalize(clazz.getSimpleName());
		StringBuilder prefixBuilded = new StringBuilder();
		if (!prefix.isEmpty()) {
			prefixBuilded.append(StringUtils.uncapitalize(prefix)).append(".");
		}
		prefixBuilded.append(className).append(".");
		
		if (Reflection.isEntity(field.getType())) {
			JoinColumn columnAnnotation = field.getAnnotation(JoinColumn.class);
			if (columnAnnotation != null) {
				String nameInAnnotation = columnAnnotation.name();
				if (nameInAnnotation != null && !nameInAnnotation.isEmpty()) {
					name = prefixBuilded.append(nameInAnnotation).toString();
				} else {
					name = prefixBuilded.append(field.getName()).toString();
				}
				mandatory = !columnAnnotation.nullable();
			} else {
				name = prefixBuilded.append(field.getName()).toString();
				mandatory = false;
			}
		} else {
			Column columnAnnotation = field.getAnnotation(Column.class);
			if (columnAnnotation != null) {
				length = columnAnnotation.length();
				String nameInAnnotation = columnAnnotation.name();
				if (nameInAnnotation != null && !nameInAnnotation.isEmpty()) {
					name = prefixBuilded.append(nameInAnnotation).toString();
				} else {
					name = prefixBuilded.append(field.getName()).toString();
				}
				mandatory = !columnAnnotation.nullable();
			} else {
				name = prefixBuilded.append(field.getName()).toString();
				mandatory = false;
			}
		}
		
		label = new StringBuilder(className).append(".page.field.").append(field.getName()).append(".label").toString();

		Class<?> fieldType = field.getType();
		if (field.isAnnotationPresent(ManyToOne.class)) {
			type = T.SELECT;
			idFieldName = Reflection.getIdField(field.getType()).getName();
			populator = new LinkedHashMap<String, String>();
			
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			GenericService<?> service = (GenericService<?>) context.getBean(StringUtils.uncapitalize(field.getType().getSimpleName()).concat("ServiceImpl"));
			ArrayList<SelectOption> tinyList = service.tinyList();
			
			for (SelectOption option : tinyList) {
				populator.put(option.getValue(), option.getText());
			}
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

	public Boolean getMandatory() {
		return mandatory;
	}

	public FormField setMandatory(Boolean nullable) {
		this.mandatory = nullable;
		return this;
	}


	public LinkedHashMap<String, String> getPopulator() {
		return populator;
	}

	public FormField setPopulator(LinkedHashMap<String, String> populator) {
		this.populator = populator;
		return this;
	}

	public String getIdFieldName() {
		return idFieldName;
	}

	public FormField setIdFieldName(String idFieldName) {
		this.idFieldName = idFieldName;
		return this;
	}
	
	@Override
	public String toString() {
		return "FormField [type=" + type + ", label=" + label + ", name=" + name + ", length=" + length + ", mandatory=" + mandatory + "]";
	}
}
