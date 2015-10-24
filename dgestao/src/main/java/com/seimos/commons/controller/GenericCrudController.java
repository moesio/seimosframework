package com.seimos.commons.controller;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.seimos.commons.hibernate.Filter;
import com.seimos.commons.hibernate.Filter.Wildcard;
import com.seimos.commons.hibernate.Filters;
import com.seimos.commons.reflection.Reflection;
import com.seimos.commons.service.GenericService;
import com.seimos.commons.web.formbuilder.SelectOption;
import com.seimos.commons.web.formbuilder.Page;

/**
 * Generic Controller for CRUD encapsulation. Subclasses must be annotated with both
 * @org.springframework.stereotype.Controller
 * @org.springframework.web.bind.annotation.RequestMapping(value = <"models">)
 * 
 * "models" stands for Model type
 * 
 * Must implement the abstract method getService() with its related service, usually interface <Model>Service. 
 * This constructor should be @Autowired annotated
 * 
 * This controller evaluates URL in RESTFul standard for CRUD as follows
 * 
 * /models/         - method GET, retrieves a complete list
 * /models/{id}     - method GET, retrieves a Model which id is {id}
 * /models/filter   - method GET, retrieves a Model using Model received on body as filter
 * /models          - method PUT, creates a new record of type Model. A Model in json format must be sent in body request
 * /models/batch    - method PUT, creates a new record of type Model for each of the list. A Model in json format must be sent in body request
 * /models          - method POST, update a record of type Model. A Model or a list of Model in json format must be sent in body request
 * /models/{id}     - method DELETE, delete a record of type Model which id is {id}
 * 
 * 
 * @author Moesio Medeiros 	
 * @date Thu Apr 20 17:45:29 BRT 2012
 * 
 */
public abstract class GenericCrudController<Entity> {

	private static final Logger logger = LoggerFactory.getLogger(GenericCrudController.class);
	private HashMap<Class<?>, Page> formCache = new HashMap<Class<?>, Page>();
	private Class<Entity> entityClass;
	public Environment env;
	private ReloadableResourceBundleMessageSource messageSource;

	public abstract GenericService<Entity> getService();

	@SuppressWarnings("unchecked")
	public GenericCrudController() {
		this.entityClass = (Class<Entity>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Autowired
	public void setEnv(Environment env) {
		this.env = env;
	}

	@Autowired
	public void setMessageSource(ReloadableResourceBundleMessageSource messageSource) {
		this.messageSource = messageSource;
	}

	private Page createPage(Model model) {
		Page page = null;
		// TODO Fazer um cache para diminuir o tempo da reflexão. 
		// Mas considerar que os campos manyToOne devem ser atualizados. 
		// e que estão sendo populados no Formfield
		//		if (formCache.containsKey(entityClass)){
		//		page = formCache.get(entityClass);
		//		} else {
		page = new Page(entityClass);
		//			formCache.put(entityClass, page);
		//		}
		model.addAttribute(page);
		//		env.getProperty("app.name");
		//		BindException errors = new BindException(bindingResult)
		return page;
	}

	private String getEntitySimpleName() {
		return StringUtils.uncapitalize(entityClass.getSimpleName());
	}

	/**
	 * Creates a new record of type Model. A Model in json format must be sent in body request
	 * 
	 * @param entity
	 * @return TRUE - Success / FALSE - Failed
	 * @throws Exception 
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@Transactional
	//		@ExceptionHandler
	public ModelAndView create(@ModelAttribute Entity entity, RedirectAttributes redirect) throws Exception { 
		// Caso queira submeter direto do formulário, senão causa erro 415
		try {
			getService().create(entity);
			redirect.addFlashAttribute(entity);
			return new ModelAndView("redirect:./grid");
		} catch (Exception e) {
			logger.error("Create exception for " + entity, e);
			throw e;
		}
	}

	@RequestMapping(value = "/createAjax", method = RequestMethod.POST)
	@Transactional
	@ResponseBody
	public Entity createAjax(@RequestBody Entity entity) throws Exception {
		return getService().create(entity);
	}

	/**
	 * Creates a batch of new records of type Model. A list of Model in json format must be sent in body request
	 * 
	 * @param entities - a list of Model
	 * @return
	 */
	@RequestMapping(value = "/batch", method = RequestMethod.POST)
	@ResponseBody
	public Boolean create(@RequestBody List<Entity> entities) {
		for (Entity model : entities) {
			try {
				if (create(model, null) != null) {
					continue;
				} else {
					return false;
				}
			} catch (Exception e) {
				logger.error("While batch, 'create' thown an exception for " + model, e);
			}
		}
		return true;
	}

	/**
	 * Retrieves a complete list of Model without any filter
	 * 
	 * @return List<Model>
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	@Transactional(readOnly = true)
	public List<Entity> list() {
		return getService().list();
	}

	/**
	 * It can be used for generate a list to use in <select>
	 * 
	 * @return
	 */
	@RequestMapping(value = "/tinyList", method = RequestMethod.GET)
	@ResponseBody
	@Transactional(readOnly = true)
	public List<SelectOption> tinyList() {

		String entityName = getEntitySimpleName();
		String value = messageSource.getMessage(entityName.concat(".tinyList.value"), null, null, null);
		String label = messageSource.getMessage(entityName.concat(".tinyList.label"), null, null, null);

		StringBuilder newLabel;
		Filters filters = new Filters();

		if (value == null) {
			value = Reflection.getIdField(entityClass).getName();
		}
		filters.add(new Filter(value));

		if (label == null) {
			filters.add(new Filter("*", Wildcard.YES));
		} else {
			String[] split = label.split("\\+");
			for (String labelComponent : split) {
				if (!labelComponent.contains("\"") && !labelComponent.contains("\'")) {
					filters.add(new Filter(labelComponent.trim()));
				}
			}
		}

		ArrayList<SelectOption> options = new ArrayList<SelectOption>();
		List<Entity> list = getService().find(filters);
		SelectOption selectOption;
		for (Entity item : list) {
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

		return options;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public String newForm(Model model) {
		Page page = createPage(model);
		page.setData("creation");
		try {
			model.addAttribute(getEntitySimpleName(), entityClass.newInstance());
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "edit";
	}

	/**
	 * Retrieves a Model which id is {id}
	 * 
	 * @param id
	 * @return Model
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	@Transactional(readOnly = true)
	public Entity findByID(@PathVariable Integer id) {
		Filters filters = new Filters().add(new Filter("id", id)).add(new Filter("*", Wildcard.YES));
		return getService().findUnique(filters);
	}

	/**
	 * Retrieves a Model using model as example 
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/filter", method = RequestMethod.POST)
	@ResponseBody
	@Transactional(readOnly = true)
	public List<Entity> findByExample(@RequestBody Entity entity) {
		return getService().find(entity);
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	@Transactional(readOnly = true)
//	@ResponseBody
	public String editForm(Model model, @PathVariable Integer id) {
		createPage(model);
		Entity entity = getService().findById(id);
		model.addAttribute(getEntitySimpleName(), entity);
		return "edit";
//		return "<html><body>Ediçasdfasdfasdasdfão " + id + "</body></html>";
	}

	//	@RequestMapping(value = "/grid", method = RequestMethod.GET)
	//	@ResponseBody
	//	public String grid(@ModelAttribute Entity entity) {
	//		List<Entity> list = getService().find(entity);
	//		return "<html><body>Grid</body></html>";
	//	}

	@RequestMapping(value = "/grid", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	//	public String grid(@RequestBody Entity entity) {
	public String grid(@ModelAttribute Entity entity, Model model, Integer start, Integer rows) {
		List<Entity> list = getService().find(entity);
		createPage(model);
		model.addAttribute("list", list);
		return "grid";
	}

	/**
	 * Update a record of type Model. A Model or a list of Model in json format must be sent in body request
	 * 
	 * {id} is prompted at URL for matching to client layer, although it's not used
	 * 
	 * @param entity
	 * @return TRUE - Success / FALSE - Failed
	 */
	@RequestMapping(value = "/update/{id}", method = RequestMethod.PATCH)
	@ResponseBody
	@Transactional
	public Boolean update(@RequestBody Entity entity) {
		try {
			getService().update(entity);
		} catch (Exception e) {
			logger.error("Update exception for " + entity, e);
			return false;
		}
		return true;
	}

	/**
	 * Deletes a record of type Model which id is {id}
	 * 
	 * @param typeJson
	 * @return TRUE - Success / FALSE - Failed
	 */
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	@Transactional
	public Boolean remove(@PathVariable Integer id) {

		try {
			getService().remove(id);
			return true;
		} catch (Exception e) {
			logger.error("Delete exception for " + id, e);
			return false;
		}
	}

}
