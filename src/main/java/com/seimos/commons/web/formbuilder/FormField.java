package com.seimos.commons.web.formbuilder;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final long serialVersionUID = 2748206626715269407L;

	protected static final Logger logger = LoggerFactory.getLogger(FormField.class);

	private T type;

	private String label;

	private String name;

	private Integer length = 255;

	private Boolean mandatory;

	private LinkedHashMap<String, String> populator;

	private String idFieldName;

	private String entity;

	public enum T {
		BOOLEAN, DATE, DETAIL, DOUBLE, ENUM, HIDDEN, INTEGER, SELECT, TEXT, UNKNOWN;
	}

	public FormField(Class<?> root, String fieldPath) {
		try {
			Field field = findFieldInDepth(root, fieldPath);
			if (field != null) {

				String clazzName = StringUtils.uncapitalize(root.getSimpleName());
				label = clazzName.concat(".page.field.").concat(fieldPath);
				name = clazzName.concat(".").concat(fieldPath);

				Class<?> fieldType = field.getType();
				entity = StringUtils.uncapitalize(fieldType.getSimpleName());

				if (field.isAnnotationPresent(Id.class)) {
					type = T.HIDDEN;
				} else if (field.isAnnotationPresent(ManyToOne.class)) {
					type = T.SELECT;
					idFieldName = Reflection.getIdField(field.getType()).getName();
					generatePopulator(field.getType());
				} else if (fieldType == Boolean.class) {
					type = T.BOOLEAN;
				} else if (isDateType(fieldType)) {
					type = T.DATE;
				} else if (field.isAnnotationPresent(OneToMany.class)) {
					type = T.DETAIL;
					Class<?> typeClass = Reflection.getGenericParameter(field.getGenericType());
					idFieldName = Reflection.getIdField(typeClass).getName();
					generatePopulator(typeClass);
				} else if (fieldType == Enum.class || fieldType.getSuperclass() == Enum.class) {
					type = T.ENUM;
				} else if (fieldType == Integer.class) {
					type = T.INTEGER;
				} else if (fieldType == String.class) {
					type = T.TEXT;
				} else if (fieldType == Double.class || fieldType == Float.class) {
					type = T.DOUBLE;
				} else {
					// TODO O que fazer quando for OneToOne?
					type = T.TEXT;
				}

				if (Reflection.isEntity(field.getType())) {
					JoinColumn columnAnnotation = field.getAnnotation(JoinColumn.class);
					if (columnAnnotation != null) {
						mandatory = !columnAnnotation.nullable();
					} else {
						mandatory = false;
					}
				} else {
					Column columnAnnotation = field.getAnnotation(Column.class);
					if (columnAnnotation != null) {
						length = columnAnnotation.length();
						mandatory = !columnAnnotation.nullable();
					} else {
						mandatory = false;
					}
				}
			}
		} catch (NoSuchFieldException e) {
			type = T.UNKNOWN;
			label = "";
			name = "";
			length = 0;
			mandatory = false;
			populator = null;
			idFieldName = "";
		}
	}

	private boolean isDateType(Class<?> fieldType) {
		return fieldType == Calendar.class || fieldType == Date.class || fieldType == LocalDate.class;
	}

	private Field findFieldInDepth(Class<?> root, String fieldPath) throws NoSuchFieldException, SecurityException {
		if (fieldPath.contains(".")) {
			String f = fieldPath.substring(0, fieldPath.indexOf("."));
			String sub = fieldPath.substring(fieldPath.indexOf(".") + 1);
			Class<?> r = root.getDeclaredField(f).getType();
			return findFieldInDepth(r, sub);
		} else {
			try {
				return root.getDeclaredField(fieldPath);
			} catch (NoSuchFieldException e) {
				if (root.getSuperclass() != Object.class) {
					return findFieldInDepth(root.getSuperclass(), fieldPath);
				}
				throw new NoSuchFieldException(fieldPath);
			}
		}
	}

	private void generatePopulator(Class<?> clazz) {
		populator = new LinkedHashMap<String, String>();
		try {
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();

			if (context != null) {
				GenericService<?> service;
				String[] serviceBeanNames = context
						.getBeanNamesForAnnotation(org.springframework.stereotype.Service.class);
				for (String beanName : serviceBeanNames) {
					Type serviceType = context.getBean(beanName).getClass().getGenericSuperclass();
					if (Reflection.getGenericParameter(serviceType) == clazz) {
						service = (GenericService<?>) context.getBean(beanName);

						ArrayList<SelectOption> tinyList = service.tinyList();

						for (SelectOption option : tinyList) {
							populator.put(option.getValue(), option.getText());
						}
						break;
					}
				}
			}
		} catch (HibernateException e) {
			logger.warn(
					"field populator is not necessary while validation. Cautionn if this message appears while other operations.");
		}
	}

	public T getType() {
		return type;
	}

	public String getLabel() {
		return label;
	}

	public String getName() {
		return name;
	}

	public Integer getLength() {
		return length;
	}

	public Boolean getMandatory() {
		return mandatory;
	}

	public LinkedHashMap<String, String> getPopulator() {
		return populator;
	}

	public String getIdFieldName() {
		return idFieldName;
	}

	public String getEntity() {
		return entity;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FormField other = (FormField) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FormField [type=" + type + ", label=" + label + ", name=" + name + ", length=" + length + ", mandatory="
				+ mandatory + ", idFieldName=" + idFieldName + ", entity=" + entity + "]";
	}

}
