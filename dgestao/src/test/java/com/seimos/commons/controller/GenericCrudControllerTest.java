package com.seimos.commons.controller;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.sitemesh.webapp.WebAppContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.seimos.commons.web.config.WebAppConfig;
import com.seimos.dgestao.config.TestContext;
import com.seimos.dgestao.domain.Genero;
//import com.seimos.dgestao.domain.Genero.Builder;
import com.seimos.dgestao.service.GeneroService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Array;
import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestContext.class, WebAppConfig.class })
@WebAppConfiguration
public class GenericCrudControllerTest extends TestCase {

	private MockMvc mockMvc;

//	@Autowired
	@Mock
	private GeneroService generoService;

	@Autowired
//	@Mock
//	private WebApplicationContext webApplicationContext;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
//		Mockito.reset(generoService);
//		MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	public void testGetService() {
		// fail("Not yet implemented"); // TODO
	}

	public void testGenericCrudController() {
		// fail("Not yet implemented"); // TODO
	}

	public void testSetEnv() {
		// fail("Not yet implemented"); // TODO
	}

	public void testSetMessageSource() {
		// fail("Not yet implemented"); // TODO
	}

	public void testCreateEntityRedirectAttributes() {
		// fail("Not yet implemented"); // TODO
		//		Genero genero01 = new Genero().setId(1).setNome("Genero 1");
		//		System.out.println(genero01);
		//		
		//		when(generoService.list()).thenReturn(Arrays.asList(genero01));

		//		mockMvc.perform(requestBuilder)
	}

	public void testCreateAjax() {
		// fail("Not yet implemented"); // TODO
	}

	public void testUpdateAjax() {
		// fail("Not yet implemented"); // TODO
	}

	public void testCreateListOfEntity() {
		// fail("Not yet implemented"); // TODO
	}

	public void testList() throws Exception {
//		Genero genero01 = new Genero.Builder().id(1).nome("Gênero 01").build();
//		Genero genero02 = new Genero.Builder().id(1).nome("Gênero 02").build();
//
//		when(generoService.list()).thenReturn(Arrays.asList(genero01, genero02));
//
//		mockMvc.perform(get("/genero/list"))
//				//
//				.andExpect(status().isOk())
//				//
//				.andExpect(view().name("todo/list"))
//				//
//				.andExpect(forwardedUrl("/WEB-INF/jsp/todo/list.jsp"))
//				//
//				.andExpect(model().attribute("todos", hasSize(2)))
//				//
//				.andExpect(model().attribute("todos", hasItem(allOf(hasProperty("id", is(1L)), hasProperty("description", is("Lorem ipsum")), hasProperty("title", is("Foo"))))))
//				.andExpect(model().attribute("todos", hasItem(allOf(hasProperty("id", is(2L)), hasProperty("description", is("Lorem ipsum")), hasProperty("title", is("Bar"))))));
//
//		verify(generoService, times(1)).list();
//		verifyNoMoreInteractions(generoService);

	}

	public void testTinyList() {
		// fail("Not yet implemented"); // TODO
	}

	public void testNewForm() {
		// fail("Not yet implemented"); // TODO
	}

	public void testFindByID() {
		// fail("Not yet implemented"); // TODO
	}

	public void testFindByExample() {
		// fail("Not yet implemented"); // TODO
	}

	public void testEditForm() {
		// fail("Not yet implemented"); // TODO
	}

	public void testGrid() {
		// fail("Not yet implemented"); // TODO
	}

	public void testUpdate() {
		// fail("Not yet implemented"); // TODO
	}

	public void testRemove() {
		// fail("Not yet implemented"); // TODO
	}

	public void testObject() {
		// fail("Not yet implemented"); // TODO
	}

	public void testGetClass() {
		// fail("Not yet implemented"); // TODO
	}

	public void testHashCode() {
		// fail("Not yet implemented"); // TODO
	}

	public void testEquals() {
		// fail("Not yet implemented"); // TODO
	}

	public void testClone() {
		// fail("Not yet implemented"); // TODO
	}

	public void testToString() {
		// fail("Not yet implemented"); // TODO
	}

	public void testNotify() {
		// fail("Not yet implemented"); // TODO
	}

	public void testNotifyAll() {
		// fail("Not yet implemented"); // TODO
	}

	public void testWaitLong() {
		// fail("Not yet implemented"); // TODO
	}

	public void testWaitLongInt() {
		// fail("Not yet implemented"); // TODO
	}

	public void testWait() {
		// fail("Not yet implemented"); // TODO
	}

	public void testFinalize() {
		// fail("Not yet implemented"); // TODO
	}

}
