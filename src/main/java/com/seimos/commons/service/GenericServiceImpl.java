package com.seimos.commons.service;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.util.StringUtils;

import com.seimos.commons.dao.GenericDao;
import com.seimos.commons.hibernate.Filter;
import com.seimos.commons.hibernate.Filters;
import com.seimos.commons.reflection.Reflection;
import com.seimos.commons.web.formbuilder.SelectOption;

public abstract class GenericServiceImpl<Domain, Dao extends GenericDao<Domain>> implements GenericService<Domain> {

	protected static final Logger logger = LoggerFactory.getLogger(GenericServiceImpl.class);
	private ReloadableResourceBundleMessageSource messageSource;
	private Class<Domain> entityClass;

	public abstract GenericDao<Domain> getDao();

	public GenericServiceImpl() {
		this.entityClass = (Class<Domain>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	@Autowired
	public void setMessageSource(ReloadableResourceBundleMessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public Domain create(Domain entity) {
		return getDao().create(entity);
	}

	public void createOrUpdate(Domain entity) {
		getDao().createOrUpdate(entity);
	}

	public Domain retrieve(Integer id) {
		return getDao().retrieve(id);
	}

	public Domain update(Domain entity) {
		return getDao().update(entity);
	}

	public void remove(Serializable id) throws InstantiationException, IllegalAccessException {
		getDao().remove(id);
	}

	public void remove(Domain entity) {
		getDao().remove(entity);
	}

	public List<Domain> list() {
		return getDao().list();
	}

	public ArrayList<SelectOption> tinyList() {
		String entityName = getEntitySimpleName();

		String value = messageSource.getMessage(entityName.concat(".tinyList.value"), null, null, null);
		String label = messageSource.getMessage(entityName.concat(".tinyList.label"), null, null, null);

		StringBuilder newLabel;
		Filters filters = new Filters();

		if (value == null) {
			logger.debug("There's no key \"" + entityName.concat(".tinyList.value") + "\" in message resource bundle.");
			value = Reflection.getIdField(entityClass).getName();
		}
		filters.add(new Filter(value));

		if (label == null) {
			logger.debug("There's no key \"" + entityName.concat(".tinyList.label") + "\" in message resource bundle.");
			filters.add(new Filter("*"));
		} else {
			String[] split = label.split("\\+");
			for (String labelComponent : split) {
				if (!labelComponent.contains("\"") && !labelComponent.contains("\'")) {
					filters.add(new Filter(labelComponent.trim()));
				}
			}
		}

		String sortBy = messageSource.getMessage(entityName.concat(".tinyList.sortBy"), null, null, null);
		if (sortBy != null) {
			filters.add(new Filter(sortBy, Filter.Order.ASC));
		}

		ArrayList<SelectOption> options = new ArrayList<SelectOption>();
		Integer listSize = null;
		String tinyListMaxSize = messageSource.getMessage(entityName.concat(".tinyList.maxSize"), null, null, null);
		if (tinyListMaxSize == null) {
			tinyListMaxSize = messageSource.getMessage("form.tinyList.maxSize", null, null, null);
		}
		try {
			listSize = Integer.valueOf(tinyListMaxSize);
		} catch (NumberFormatException e) {
			logger.debug("Property ".concat(entityName.concat(".tinyList.maxSize"))
					.concat(" at resource bundle is not a number. No limit assummed."));
		}

		List<Domain> list;
		if (listSize == null) {
			list = find(filters);
		} else {
			list = find(filters, 0, listSize);
		}
		SelectOption selectOption;
		for (Domain item : list) {
			selectOption = new SelectOption().setValue(Reflection.invoke(item, value).toString());

			if (label == null) {
				selectOption.setText(item.toString());
			} else {
				newLabel = new StringBuilder();
				String[] split = label.split("\\+");
				for (String labelComponent : split) {
					if (labelComponent.contains("\"") || labelComponent.contains("\'")) {
						newLabel.append(labelComponent.replace("\"", "").replace("\'", ""));
					} else {
						newLabel.append(Reflection.invoke(item, labelComponent.trim()));
					}
				}
				selectOption.setText(newLabel.toString());
			}
			options.add(selectOption);
		}
		if (listSize != null && options.size() == listSize) {
			String overloadMessage = messageSource.getMessage(entityName.concat(".tinyList.maxSize.overloaded"), null,
					null, null);
			if (overloadMessage == null) {
				overloadMessage = messageSource.getMessage("form.tinyList.maxSize.overloaded", null, null, null);
			}
			if (overloadMessage == null) {
				overloadMessage = "List overload maxSize";
			}
			options.add(new SelectOption().setValue("").setText(overloadMessage));
		}
		return options;
	}

	public List<Domain> find(Filters filters) {
		return getDao().find(filters, null, null);
	}

	public List<Domain> find(Filters filters, Integer firstResult, Integer maxResult) {
		return getDao().find(filters, firstResult, maxResult);
	}

	public List<Domain> find(List<Filter> filters) {
		return getDao().find(filters, null, null);
	}

	public List<Domain> find(List<Filter> filters, Integer firstResult, Integer maxResult) {
		return getDao().find(filters, firstResult, maxResult);
	}

	public List<Domain> find(Filter... filters) {
		return getDao().find(filters);
	}

	public List<Domain> find(Domain entity) {
		return getDao().find(entity);
	}

	public List<Domain> find(Domain entity, Integer firstResult, Integer maxResult) {
		return getDao().find(entity, firstResult, maxResult);
	}

	public List<Domain> sortedFind(Domain entity, String... order) {
		return getDao().sortedFind(entity, order);
	}

	public Domain findUnique(Filters filters) {
		return getDao().findUnique(filters);
	}

	public Domain findUnique(Filter... filters) {
		return getDao().findUnique(filters);
	}

	public Domain findUnique(Domain entity) {
		return getDao().findUnique(entity);
	}

	public Domain findUnique(List<Filter> filters) {
		return getDao().findUnique(filters);
	}

	public Domain findById(Object id) {
		return getDao().findById(id);
	}

	private String getEntitySimpleName() {
		return StringUtils.uncapitalize(entityClass.getSimpleName());
	}
}
