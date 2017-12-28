package com.seimos.commons.controller;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import com.seimos.commons.hibernate.Filter;
import com.seimos.commons.hibernate.Filters;
import com.seimos.commons.reflection.Reflection;
import com.seimos.commons.service.GenericService;
import com.seimos.commons.validator.GenericValidator;
import com.seimos.commons.web.formbuilder.DataTable;
import com.seimos.commons.web.formbuilder.Page;
import com.seimos.commons.web.formbuilder.SelectOption;

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
	@SuppressWarnings("unused")
	private HashMap<Class<?>, Page> formCache = new HashMap<Class<?>, Page>();
	private Class<Entity> entityClass;
	private ReloadableResourceBundleMessageSource messageSource;
	public Environment env;

	public abstract GenericService<Entity> getService();

	public GenericCrudController() {
		this.entityClass = (Class<Entity>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	@Autowired
	public void setEnv(Environment env) {
		this.env = env;
	}

	@Autowired
	public void setMessageSource(ReloadableResourceBundleMessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(getValidator());
	}

	public abstract GenericValidator<Entity> getValidator();

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

	private String getGridPageSize() {
		return messageSource.getMessage(getEntitySimpleName().concat(".grid.page.size"), null,
				messageSource.getMessage("grid.page.size", null, "5", null), null);
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
	// @ModelAttribute anotaded attribute MUST BE FOLLOWED by BindingResult attribute, else, error is thrown
	public ModelAndView create(@Valid @ModelAttribute Entity entity, BindingResult result, RedirectAttributes redirect)
			throws Exception {
		try {
			if (result.hasErrors()) {
				redirect.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + getEntitySimpleName(), result);
				redirect.addFlashAttribute(getEntitySimpleName(), entity);
				return new ModelAndView(UrlBasedViewResolver.REDIRECT_URL_PREFIX.concat("./"));
			}
			getService().create(entity);
			return new ModelAndView(UrlBasedViewResolver.REDIRECT_URL_PREFIX.concat("./grid/0/" + getGridPageSize()));
		} catch (Exception e) {
			logger.error("Create throwns an exception for " + entity, e);
			throw e;
		}
	}

	@RequestMapping(value = "/createAjax", method = RequestMethod.POST)
	@Transactional
	@ResponseBody
	public Entity createAjax(@RequestBody Entity entity) throws Exception {
		getService().create(entity);
		return entity;
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
		return getService().tinyList();
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public String newForm(Model model) {
		Page page = createPage(model);
		// TODO Is there a way to avoid this property and read if 'id' is null or not?
		page.addProperty("creation", true);
		try {
			if (model.asMap().get(BindingResult.MODEL_KEY_PREFIX + getEntitySimpleName()) == null) {
				model.addAttribute(getEntitySimpleName(), entityClass.newInstance());
			}
		} catch (Exception e) {
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
		Filters filters = new Filters().add(new Filter("id", id)).add(new Filter("*"));
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
	public String editForm(Model model, @PathVariable Integer id) {
		createPage(model);
		Entity entity = getService().findById(id);
		if (model.asMap().get(BindingResult.MODEL_KEY_PREFIX + getEntitySimpleName()) == null) {
			model.addAttribute(getEntitySimpleName(), entity);
		}
		return "edit";
	}

	@RequestMapping(value = "/dataTable", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	@ResponseBody
	public DataTable<Entity> dataTable(@ModelAttribute Entity entity, Model model) {
		DataTable<Entity> dataTable = new DataTable<Entity>();
		dataTable.setDraw(1);
		dataTable.setRecordsFiltered(3);
		dataTable.setRecordsTotal(10);
		dataTable.setData(getService().find(entity));
		return dataTable;
	}

	@RequestMapping(value = "/grid", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public String grid(@ModelAttribute Entity entity, Model model) {
		return UrlBasedViewResolver.REDIRECT_URL_PREFIX.concat("./grid/0/" + getGridPageSize());
	}

	@RequestMapping(value = "/grid/{start}/{rows}", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public String grid(@ModelAttribute Entity entity, Model model, @PathVariable Integer start,
			@PathVariable Integer rows) {
		if (rows != null && rows == 0) {
			rows = Integer.valueOf(getGridPageSize());
		}
		// TODO Define a way of sorting fields
		List<Entity> list = getService().find(entity, start, rows);
		createPage(model);
		model.addAttribute("list", list);
		model.addAttribute("entity", getEntitySimpleName());
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
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@Transactional
	public ModelAndView update(@Valid @ModelAttribute Entity entity, BindingResult result, RedirectAttributes redirect)
			throws Exception {
		try {
			if (result.hasErrors()) {
				redirect.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + getEntitySimpleName(), result);
				redirect.addFlashAttribute(getEntitySimpleName(), entity);
				String id = Reflection.invoke(entity, Reflection.getIdField(entityClass).getName()).toString();
				return new ModelAndView(UrlBasedViewResolver.REDIRECT_URL_PREFIX.concat("./edit/".concat(id)));
			}
			getService().update(entity);
			//			redirect.addFlashAttribute(entity);
			return new ModelAndView(UrlBasedViewResolver.REDIRECT_URL_PREFIX.concat("./grid/0/" + getGridPageSize()));
		} catch (Exception e) {
			logger.error("Update exception for " + entity, e);
			throw e;
		}
	}

	@RequestMapping(value = "/updateAjax", method = RequestMethod.POST)
	@Transactional
	@ResponseBody
	public Entity updateAjax(@RequestBody Entity entity) throws Exception {
		getService().update(entity);
		return entity;
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
