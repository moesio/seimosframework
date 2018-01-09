package com.seimos.commons.web.formbuilder;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
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

	private Field field;

	public enum T {
		BOOLEAN, DATE, DETAIL, DOUBLE, ENUM, HIDDEN, INTEGER, SELECT, TEXT;

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

	//	protected FormField(String prefix, String embeddedFieldName, Field field) {
	//		assertNotNull(prefix);
	//		assertNotNull(field);
	//		StringBuilder prefixBuilded = new StringBuilder();
	//		String className;
	//		//		if (!Reflection.isEmbedded(clazz)) {
	//		if (StringUtils.isEmpty(embeddedFieldName)) {
	//			className = StringUtils.uncapitalize(field.getDeclaringClass().getSimpleName());
	//			prefixBuilded.append(className).append(".");
	//			label = className.concat(".page.field.").concat(field.getName()).concat(".label");
	//		} else {
	//			prefixBuilded.append(prefix).append(".");
	//			className = StringUtils.uncapitalize(prefix);
	//			label = prefix.concat(".page.field.").concat(embeddedFieldName).concat(".").concat(field.getName())
	//					.concat(".label");
	//		}
	//
	//		if (Reflection.isEntity(field.getType())) {
	//			JoinColumn columnAnnotation = field.getAnnotation(JoinColumn.class);
	//			if (columnAnnotation != null) {
	//				String nameInAnnotation = columnAnnotation.name();
	//				if (nameInAnnotation != null && !nameInAnnotation.isEmpty()) {
	//					name = prefixBuilded.append(nameInAnnotation).toString();
	//				} else {
	//					name = prefixBuilded.append(field.getName()).toString();
	//				}
	//				mandatory = !columnAnnotation.nullable();
	//			} else {
	//				if (StringUtils.isEmpty(embeddedFieldName)) {
	//					name = prefixBuilded.append(field.getName()).toString();
	//				} else {
	//					name = prefixBuilded.append(embeddedFieldName).append(".").append(field.getName()).toString();
	//				}
	//				mandatory = false;
	//			}
	//		} else {
	//			Column columnAnnotation = field.getAnnotation(Column.class);
	//			if (columnAnnotation != null) {
	//				length = columnAnnotation.length();
	//				String nameInAnnotation = columnAnnotation.name();
	//				if (nameInAnnotation != null && !nameInAnnotation.isEmpty()) {
	//					name = prefixBuilded.append(nameInAnnotation).toString();
	//				} else {
	//					name = prefixBuilded.append(field.getName()).toString();
	//				}
	//				mandatory = !columnAnnotation.nullable();
	//			} else {
	//				if (StringUtils.isEmpty(embeddedFieldName)) {
	//					name = prefixBuilded.append(field.getName()).toString();
	//				} else {
	//					name = prefixBuilded.append(embeddedFieldName).append(".").append(field.getName()).toString();
	//				}
	//				mandatory = false;
	//			}
	//		}
	//	}

	public FormField(Class<?> root, String fieldPath) {
		try {
			field = findFieldInDepth(root, fieldPath);

			String clazzName = StringUtils.uncapitalize(root.getSimpleName());
			label = clazzName.concat(".page.field.").concat(fieldPath);
			name = clazzName.concat(".").concat(fieldPath);

			Class<?> fieldType = field.getType();
			if (field.isAnnotationPresent(Id.class)) {
				type = T.HIDDEN;
			} else if (field.isAnnotationPresent(ManyToOne.class)) {
				type = T.SELECT;
				idFieldName = Reflection.getIdField(field.getType()).getName();
				generatePopulator(field.getType());
			} else if (fieldType == Boolean.class) {
				type = T.BOOLEAN;
			} else if (fieldType == Calendar.class) {
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

		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}

	private Field findFieldInDepth(Class<?> root, String fieldPath) throws NoSuchFieldException, SecurityException {
		if (fieldPath.contains(".")) {
			String f = fieldPath.substring(0, fieldPath.indexOf("."));
			String sub = fieldPath.substring(fieldPath.indexOf(".") + 1);
			Class<?> r = root.getDeclaredField(f).getType();
			return findFieldInDepth(r, sub);
		} else {
			return root.getDeclaredField(fieldPath);
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

	//	public FormField setType(T type) {
	//		this.type = type;
	//		return this;
	//	}

	public String getLabel() {
		return label;
	}

	//	public FormField setLabel(String label) {
	//		this.label = label;
	//		return this;
	//	}

	public String getName() {
		return name;
	}

	//	public FormField setName(String name) {
	//		this.name = name;
	//		return this;
	//	}

	public Integer getLength() {
		return length;
	}

	//	public FormField setLength(Integer size) {
	//		this.length = size;
	//		return this;
	//	}

	public Boolean getMandatory() {
		return mandatory;
	}

	//	public FormField setMandatory(Boolean nullable) {
	//		this.mandatory = nullable;
	//		return this;
	//	}

	public LinkedHashMap<String, String> getPopulator() {
		return populator;
	}

	//	public FormField setPopulator(LinkedHashMap<String, String> populator) {
	//		this.populator = populator;
	//		return this;
	//	}

	public String getIdFieldName() {
		return idFieldName;
	}

	//	public FormField setIdFieldName(String idFieldName) {
	//		this.idFieldName = idFieldName;
	//		return this;
	//	}

	@Override
	public String toString() {
		return "FormField [type=" + type + ", label=" + label + ", name=" + name + ", length=" + length + ", mandatory="
				+ mandatory + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((field == null) ? 0 : field.hashCode());
		result = prime * result + ((idFieldName == null) ? 0 : idFieldName.hashCode());
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + ((length == null) ? 0 : length.hashCode());
		result = prime * result + ((mandatory == null) ? 0 : mandatory.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((populator == null) ? 0 : populator.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		if (field == null) {
			if (other.field != null)
				return false;
		} else if (!field.equals(other.field))
			return false;
		if (idFieldName == null) {
			if (other.idFieldName != null)
				return false;
		} else if (!idFieldName.equals(other.idFieldName))
			return false;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (length == null) {
			if (other.length != null)
				return false;
		} else if (!length.equals(other.length))
			return false;
		if (mandatory == null) {
			if (other.mandatory != null)
				return false;
		} else if (!mandatory.equals(other.mandatory))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (populator == null) {
			if (other.populator != null)
				return false;
		} else if (!populator.equals(other.populator))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

}
