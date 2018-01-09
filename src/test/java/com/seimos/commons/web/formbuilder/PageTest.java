package com.seimos.commons.web.formbuilder;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import com.seimos.commons.web.formbuilder.domain.Foo;

/**
 * @author moesio
 * @date 2018-01-06 23:37:29
 *
 */
public class PageTest {

	private static Page page;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		page = new Page(Foo.class);
	}

	@Test
	public void testGetTitle() {
		assertEquals("foo.page.title", page.getTitle());
	}

	@Test
	public void testGetEntityName() {
		assertEquals("foo", page.getEntityName());
	}

	@Test
	public void testGetFormFields() throws NoSuchFieldException, SecurityException {
		Object[] actual = page.getFormFields().toArray();
		Object[] expected = new Object[] { //
				new FormField(Foo.class, "bool"), //
				new FormField(Foo.class, "date"), //
				new FormField(Foo.class, "many"), //
				new FormField(Foo.class, "integer"), //
				new FormField(Foo.class, "doub"), //
				new FormField(Foo.class, "flo"), //
				new FormField(Foo.class, "any"), //
				new FormField(Foo.class, "idFoo"), //
				new FormField(Foo.class, "some"), //
				new FormField(Foo.class, "one"), //
				new FormField(Foo.class, "within.wbool"), //
				new FormField(Foo.class, "within.wdate"), //
				new FormField(Foo.class, "within.wmany"), //
				new FormField(Foo.class, "within.winteger"), //
				new FormField(Foo.class, "within.wdoub"), //
				new FormField(Foo.class, "within.wflo"), //
				new FormField(Foo.class, "within.wany"), //
				new FormField(Foo.class, "within.wsome"), //
				new FormField(Foo.class, "within.wone"), //
				new FormField(Foo.class, "within.deep.another"),//
		};
		assertArrayEquals(expected, actual);
	}

	//	/**
	//	 * Test method for {@link com.seimos.commons.web.formbuilder.Page#getData()}.
	//	 */
	//	@Test
	//	public void testGetData() {
	//		fail("Not yet implemented");
	//	}
	//
	//	/**
	//	 * Test method for
	//	 * {@link com.seimos.commons.web.formbuilder.Page#setData(java.lang.Object)}.
	//	 */
	//	@Test
	//	public void testSetData() {
	//		fail("Not yet implemented");
	//	}
	//
}
