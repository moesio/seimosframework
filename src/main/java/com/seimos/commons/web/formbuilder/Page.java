package com.seimos.commons.web.formbuilder;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.util.StringUtils;

import com.seimos.commons.reflection.Reflection;

/**
 * @author moesio @ gmail.com
 * @date Sep 28, 2014 12:48:24 PM
 */
public class Page implements Serializable {

	private static final long serialVersionUID = -3944698356746138568L;

	private String title;

	private List<FormField> formFields = new ArrayList<FormField>();

	private Object data;

	private HashMap<String, Object> properties = new HashMap<String, Object>();

	private String entityName;

	private Class<?> entityClass;

	// TODO Create a webForm cache to avoid reflection for every request 
	public Page(Class<?> root) {
		entityName = StringUtils.uncapitalize(root.getSimpleName());
		entityClass = root;
		title = new StringBuilder(entityName).append(".page.title").toString();
		extractFields(root, "");
	}

	private void extractFields(Class<?> entity, String path) {
		Field[] fields = Reflection.getNoTransientFields(entity);
		//		List<FormField> formFields = new ArrayList<FormField>();
		for (Field field : fields) {
			if (Reflection.isEmbedded(field.getType())) {
				//				System.out.println("--->" + field.getName());
				extractFields(field.getType(), path.concat(".").concat(field.getName()));
			} else {
				String fullPath = path.concat(".").concat(field.getName());
				formFields.add(new FormField(entityClass, fullPath.substring(1)));
				//				System.out.println(fullPath.substring(1));
			}
		}
	}

	//	private Collection<? extends FormField> extractFields(String prefix, String embeddedFieldName, Class<?> clazz) {
	//		ArrayList<FormField> formFields = new ArrayList<FormField>();
	//		Field[] noTransientFields = Reflection.getNoTransientFields(clazz);
	//		for (Field field : noTransientFields) {
	//			if (field.isAnnotationPresent(Embedded.class) || field.isAnnotationPresent(EmbeddedId.class)) {
	//				formFields.addAll(extractFields(StringUtils.uncapitalize(clazz.getSimpleName()), field.getName(),
	//						field.getType()));
	//			} else {
	//				FormField formField;
	//				if (StringUtils.isEmpty(embeddedFieldName)) {
	//					formField = new FormField(prefix, null, field);
	//				} else {
	//					formField = new FormField(prefix, embeddedFieldName, field);
	//				}
	//				formFields.add(formField);
	//			}
	//		}
	//		return formFields;
	//	}

	public String getTitle() {
		return title;
	}

	public String getEntityName() {
		return entityName;
	}

	public List<FormField> getFormFields() {
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
