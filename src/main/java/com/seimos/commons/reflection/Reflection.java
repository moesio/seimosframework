package com.seimos.commons.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.LazyInitializationException;

public class Reflection {

	public Collection<String> entityDeepPath(Class<?> clazz) {

		Collection<String> paths = new ArrayList<String>();

		createPaths(paths, clazz);

		for (String string : paths) {
			System.out.println(string);
		}

		return paths;
	}

	private void createPaths(Collection<String> paths, Class<?> clazz) {
		createPaths(paths, clazz, "");
	}

	private void createPaths(Collection<String> paths, Class<?> clazz, String embeddedField) {
		//Class<? extends Object> clazz = entidade.getClass();
		//		embeddedField = (embeddedField.equals("") ? "" : embeddedField + ".");

		for (Field field : clazz.getDeclaredFields()) {

			if (isEntity(field.getType())) {
				createPaths(paths, clazz);
			} else {
				System.out.println(field.getName());
			}
			String fieldName = field.getName();
			Method method = null;
			//			Object result = null;
			try {
				method = clazz.getMethod(Reflection.getGetter(field));

			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}

			System.out.println(fieldName);
			System.out.println(method.getName());

			/*/////////
				if (Reflection.isEntity(result.getClass())) {
					Criteria subCriteria = criteria.createCriteria(embeddedField + fieldName, JoinFragment.LEFT_OUTER_JOIN);
					createPaths(subCriteria, result);
				} else if (Reflection.isEmbedded(result.getClass())) {
					createCriteria(criteria, result, embeddedField + fieldName);
				} else { // adiciona criterio
					if (field.getType() == Boolean.class || field.getType() == Integer.class) {
						criteria.add(Restrictions.eq(embeddedField + fieldName, result));
					} else if (field.getType() == String.class && !result.equals("")) {
						criteria.add(Restrictions.ilike(embeddedField + fieldName, (String) result, MatchMode.START));
					} else if (field.getType() == Date.class) {
						criteria.add(Restrictions.eq(embeddedField + fieldName, (Date) result));
					}
				}
			}
			 */
		}
	}

	public static Class<?> getGenericParameter(Type baseClass) {
		return getGenericParameter(baseClass, 0);
	}

	public static Class<?> getGenericParameter(Type baseClass, int index) {
		return (Class<?>) ((ParameterizedType) baseClass).getActualTypeArguments()[index];
	}

	public static boolean isEntity(Class<?> clazz) {
		return clazz.isAnnotationPresent(Entity.class);
	}

	public static String getGetter(Field field) {
		String fieldName = field.getName();
		String methodName = ((field.getType() == Boolean.TYPE) ? "is" : "get") + fieldName.substring(0, 1).toUpperCase()
				+ fieldName.substring(1);
		return methodName;
	}

	public static String getGetter(String property) {
		return "get" + property.substring(0, 1).toUpperCase() + property.substring(1);
	}

	public static String getSetter(String property) {
		return "set" + property.substring(0, 1).toUpperCase() + property.substring(1);
	}

	public static boolean isEmbedded(Class<?> clazz) {
		return clazz.isAnnotationPresent(Embeddable.class);
	}

	public static List<Field> getNoTransientFields(Class<?> clazz) {
		List<Field> fields = new ArrayList<Field>();
		fields.addAll(extractFields(clazz));
		return fields;
	}

	private static List<Field> extractFields(Class<?> clazz) {
		List<Field> fields = new ArrayList<Field>();
		Field[] declaredFields = clazz.getDeclaredFields();
		for (int i = 0; i < declaredFields.length; i++) {
			Field field = clazz.getDeclaredFields()[i];
			if (!field.isAnnotationPresent(Transient.class) && !field.getName().contains("$")) {
				fields.add(field);
			}
		}
		//		if (clazz.getSuperclass() != Object.class) {
		//			fields.addAll(extractFields(clazz.getSuperclass()));
		//		}
		return fields;
	}

	public static Object invoke(Object entity, String property) {
		Class<?> clazz = entity.getClass();

		Object invocation = null;
		try {
			Method method = null;
			method = clazz.getMethod(Reflection.getGetter(property));
			invocation = method.invoke(entity);
		} catch (LazyInitializationException e) {
			throw new LazyInitializationException("");
		} catch (Exception e) {
			// em caso de não existir o método. Praticamente impossível, já que
			// estou trazendo um método preexistente
			e.printStackTrace();
		}
		return invocation;
	}

	public static boolean isEmbedded(Class<?> clazz, String attributePath) {
		Field field = null;

		try {
			String embeddedCandidateName = "";
			if (attributePath.contains(".")) {
				embeddedCandidateName = attributePath.substring(0, attributePath.indexOf("."));
				Field fieldCandidate = clazz.getDeclaredField(embeddedCandidateName);
				Class<?> clazzCandidate = fieldCandidate.getType(); // getGetter(fieldCandidate).getClass();
				int length = embeddedCandidateName.length();
				String associationPathCandidate = attributePath.substring(length + 1);
				return isEmbedded(clazzCandidate, associationPathCandidate);
			} else {
				embeddedCandidateName = attributePath;
			}
			field = clazz.getDeclaredField(embeddedCandidateName);

			return (field.isAnnotationPresent(EmbeddedId.class) || field.isAnnotationPresent(Embedded.class));
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isCollection(Class<?> clazz, String attributePath) {
		try {
			Field field = clazz.getDeclaredField(attributePath);
			if (field.getType().isAssignableFrom(List.class)) {
				return true;
			}
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static Field getIdField(Class<?> clazz) {
		Class<?> clazzParameter = clazz;
		try {
			Field[] declaredFields;
			do {
				declaredFields = clazz.getDeclaredFields();
				for (Field field : declaredFields) {
					if (field.isAnnotationPresent(Id.class))
						return field;
				}
				clazz = clazz.getSuperclass();
			} while (clazz != null);
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		throw new RuntimeException("There is no @Id annotated field in " + clazzParameter.getCanonicalName());
	}
}
