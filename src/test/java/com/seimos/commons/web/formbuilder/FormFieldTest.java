package com.seimos.commons.web.formbuilder;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.seimos.commons.web.formbuilder.domain.Foo;
import com.seimos.commons.web.formbuilder.domain.Within;

/**
 * @author moesio
 * @date 2018-01-07 11:30:23
 *
 */
public class FormFieldTest {

	@Test
	public void testGetType() throws NoSuchFieldException, SecurityException {
		assertEquals(FormField.T.BOOLEAN, new FormField(Foo.class, "bool").getType());
		assertEquals(FormField.T.DATE, new FormField(Foo.class, "date").getType());
		assertEquals(FormField.T.DETAIL, new FormField(Foo.class, "many").getType());
		assertEquals(FormField.T.DOUBLE, new FormField(Foo.class, "doub").getType());
		assertEquals(FormField.T.DOUBLE, new FormField(Foo.class, "flo").getType());
		assertEquals(FormField.T.ENUM, new FormField(Foo.class, "any").getType());
		assertEquals(FormField.T.HIDDEN, new FormField(Foo.class, "idFoo").getType());
		assertEquals(FormField.T.INTEGER, new FormField(Foo.class, "integer").getType());
		assertEquals(FormField.T.SELECT, new FormField(Foo.class, "some").getType());

		assertEquals(FormField.T.BOOLEAN, new FormField(Foo.class, "within.wbool").getType());
		assertEquals(FormField.T.DATE, new FormField(Foo.class, "within.wdate").getType());
		assertEquals(FormField.T.DETAIL, new FormField(Foo.class, "within.wmany").getType());
		assertEquals(FormField.T.DOUBLE, new FormField(Foo.class, "within.wdoub").getType());
		assertEquals(FormField.T.DOUBLE, new FormField(Foo.class, "within.wflo").getType());
		assertEquals(FormField.T.ENUM, new FormField(Foo.class, "within.wany").getType());
		assertEquals(FormField.T.INTEGER, new FormField(Foo.class, "within.winteger").getType());
		assertEquals(FormField.T.SELECT, new FormField(Foo.class, "within.wsome").getType());
		assertEquals(FormField.T.TEXT, new FormField(Foo.class, "within.deep.another").getType());
	}

	@Test
	public void testGetLabel() {
		assertEquals("foo.page.field.bool", new FormField(Foo.class, "bool").getLabel());
		assertEquals("foo.page.field.date", new FormField(Foo.class, "date").getLabel());
		assertEquals("foo.page.field.many", new FormField(Foo.class, "many").getLabel());
		assertEquals("foo.page.field.doub", new FormField(Foo.class, "doub").getLabel());
		assertEquals("foo.page.field.flo", new FormField(Foo.class, "flo").getLabel());
		assertEquals("foo.page.field.any", new FormField(Foo.class, "any").getLabel());
		assertEquals("foo.page.field.idFoo", new FormField(Foo.class, "idFoo").getLabel());
		assertEquals("foo.page.field.integer", new FormField(Foo.class, "integer").getLabel());
		assertEquals("foo.page.field.some", new FormField(Foo.class, "some").getLabel());

		assertEquals("foo.page.field.within.wbool", new FormField(Foo.class, "within.wbool").getLabel());
		assertEquals("foo.page.field.within.wdate", new FormField(Foo.class, "within.wdate").getLabel());
		assertEquals("foo.page.field.within.wmany", new FormField(Foo.class, "within.wmany").getLabel());
		assertEquals("foo.page.field.within.wdoub", new FormField(Foo.class, "within.wdoub").getLabel());
		assertEquals("foo.page.field.within.wflo", new FormField(Foo.class, "within.wflo").getLabel());
		assertEquals("foo.page.field.within.wany", new FormField(Foo.class, "within.wany").getLabel());
		assertEquals("foo.page.field.within.winteger", new FormField(Foo.class, "within.winteger").getLabel());
		assertEquals("foo.page.field.within.wsome", new FormField(Foo.class, "within.wsome").getLabel());
		assertEquals("foo.page.field.within.deep.another", new FormField(Foo.class, "within.deep.another").getLabel());

		assertEquals("within.page.field.wbool", new FormField(Within.class, "wbool").getLabel());
		assertEquals("within.page.field.wdate", new FormField(Within.class, "wdate").getLabel());
		assertEquals("within.page.field.wmany", new FormField(Within.class, "wmany").getLabel());
		assertEquals("within.page.field.wdoub", new FormField(Within.class, "wdoub").getLabel());
		assertEquals("within.page.field.wflo", new FormField(Within.class, "wflo").getLabel());
		assertEquals("within.page.field.wany", new FormField(Within.class, "wany").getLabel());
		assertEquals("within.page.field.winteger", new FormField(Within.class, "winteger").getLabel());
		assertEquals("within.page.field.wsome", new FormField(Within.class, "wsome").getLabel());
		assertEquals("within.page.field.deep.another", new FormField(Within.class, "deep.another").getLabel());
		assertEquals("within.page.field.deep.superDeep", new FormField(Within.class, "deep.superDeep").getLabel());
	}

	@Test
	public void testGetName() {
		assertEquals("foo.bool", new FormField(Foo.class, "bool").getName());
		assertEquals("foo.date", new FormField(Foo.class, "date").getName());
		assertEquals("foo.many", new FormField(Foo.class, "many").getName());
		assertEquals("foo.doub", new FormField(Foo.class, "doub").getName());
		assertEquals("foo.flo", new FormField(Foo.class, "flo").getName());
		assertEquals("foo.any", new FormField(Foo.class, "any").getName());
		assertEquals("foo.idFoo", new FormField(Foo.class, "idFoo").getName());
		assertEquals("foo.integer", new FormField(Foo.class, "integer").getName());
		assertEquals("foo.some", new FormField(Foo.class, "some").getName());

		assertEquals("foo.within.wbool", new FormField(Foo.class, "within.wbool").getName());
		assertEquals("foo.within.wdate", new FormField(Foo.class, "within.wdate").getName());
		assertEquals("foo.within.wmany", new FormField(Foo.class, "within.wmany").getName());
		assertEquals("foo.within.wdoub", new FormField(Foo.class, "within.wdoub").getName());
		assertEquals("foo.within.wflo", new FormField(Foo.class, "within.wflo").getName());
		assertEquals("foo.within.wany", new FormField(Foo.class, "within.wany").getName());
		assertEquals("foo.within.winteger", new FormField(Foo.class, "within.winteger").getName());
		assertEquals("foo.within.wsome", new FormField(Foo.class, "within.wsome").getName());
		assertEquals("foo.within.deep.another", new FormField(Foo.class, "within.deep.another").getName());

		assertEquals("within.wbool", new FormField(Within.class, "wbool").getName());
		assertEquals("within.wdate", new FormField(Within.class, "wdate").getName());
		assertEquals("within.wmany", new FormField(Within.class, "wmany").getName());
		assertEquals("within.wdoub", new FormField(Within.class, "wdoub").getName());
		assertEquals("within.wflo", new FormField(Within.class, "wflo").getName());
		assertEquals("within.wany", new FormField(Within.class, "wany").getName());
		assertEquals("within.winteger", new FormField(Within.class, "winteger").getName());
		assertEquals("within.wsome", new FormField(Within.class, "wsome").getName());
		assertEquals("within.deep.another", new FormField(Within.class, "deep.another").getName());
	}

	@Test
	public void testGetLength() {
		assertEquals(new Integer(255), new FormField(Foo.class, "bool").getLength());
		assertEquals(new Integer(255), new FormField(Foo.class, "date").getLength());
		assertEquals(new Integer(255), new FormField(Foo.class, "many").getLength());
		assertEquals(new Integer(255), new FormField(Foo.class, "doub").getLength());
		assertEquals(new Integer(255), new FormField(Foo.class, "flo").getLength());
		assertEquals(new Integer(255), new FormField(Foo.class, "any").getLength());
		assertEquals(new Integer(255), new FormField(Foo.class, "idFoo").getLength());
		assertEquals(new Integer(255), new FormField(Foo.class, "integer").getLength());
		assertEquals(new Integer(255), new FormField(Foo.class, "some").getLength());

		assertEquals(new Integer(255), new FormField(Foo.class, "within.wbool").getLength());
		assertEquals(new Integer(255), new FormField(Foo.class, "within.wdate").getLength());
		assertEquals(new Integer(255), new FormField(Foo.class, "within.wmany").getLength());
		assertEquals(new Integer(255), new FormField(Foo.class, "within.wdoub").getLength());
		assertEquals(new Integer(255), new FormField(Foo.class, "within.wflo").getLength());
		assertEquals(new Integer(255), new FormField(Foo.class, "within.wany").getLength());
		assertEquals(new Integer(255), new FormField(Foo.class, "within.winteger").getLength());
		assertEquals(new Integer(255), new FormField(Foo.class, "within.wsome").getLength());
		assertEquals(new Integer(255), new FormField(Foo.class, "within.deep.another").getLength());

		assertEquals(new Integer(255), new FormField(Within.class, "wbool").getLength());
		assertEquals(new Integer(255), new FormField(Within.class, "wdate").getLength());
		assertEquals(new Integer(255), new FormField(Within.class, "wmany").getLength());
		assertEquals(new Integer(255), new FormField(Within.class, "wdoub").getLength());
		assertEquals(new Integer(255), new FormField(Within.class, "wflo").getLength());
		assertEquals(new Integer(255), new FormField(Within.class, "wany").getLength());
		assertEquals(new Integer(255), new FormField(Within.class, "winteger").getLength());
		assertEquals(new Integer(255), new FormField(Within.class, "wsome").getLength());
		assertEquals(new Integer(255), new FormField(Within.class, "deep.another").getLength());
	}

	@Test
	public void testGetMandatory() {
		assertEquals(false, new FormField(Foo.class, "bool").getMandatory());
		assertEquals(false, new FormField(Foo.class, "date").getMandatory());
		assertEquals(false, new FormField(Foo.class, "many").getMandatory());
		assertEquals(false, new FormField(Foo.class, "doub").getMandatory());
		assertEquals(false, new FormField(Foo.class, "flo").getMandatory());
		assertEquals(false, new FormField(Foo.class, "any").getMandatory());
		assertEquals(false, new FormField(Foo.class, "idFoo").getMandatory());
		assertEquals(false, new FormField(Foo.class, "integer").getMandatory());
		assertEquals(false, new FormField(Foo.class, "some").getMandatory());

		assertEquals(false, new FormField(Foo.class, "within.wbool").getMandatory());
		assertEquals(false, new FormField(Foo.class, "within.wdate").getMandatory());
		assertEquals(false, new FormField(Foo.class, "within.wmany").getMandatory());
		assertEquals(false, new FormField(Foo.class, "within.wdoub").getMandatory());
		assertEquals(false, new FormField(Foo.class, "within.wflo").getMandatory());
		assertEquals(false, new FormField(Foo.class, "within.wany").getMandatory());
		assertEquals(false, new FormField(Foo.class, "within.winteger").getMandatory());
		assertEquals(false, new FormField(Foo.class, "within.wsome").getMandatory());
		assertEquals(false, new FormField(Foo.class, "within.deep.another").getMandatory());

		assertEquals(false, new FormField(Within.class, "wbool").getMandatory());
		assertEquals(false, new FormField(Within.class, "wdate").getMandatory());
		assertEquals(false, new FormField(Within.class, "wmany").getMandatory());
		assertEquals(false, new FormField(Within.class, "wdoub").getMandatory());
		assertEquals(false, new FormField(Within.class, "wflo").getMandatory());
		assertEquals(false, new FormField(Within.class, "wany").getMandatory());
		assertEquals(false, new FormField(Within.class, "winteger").getMandatory());
		assertEquals(false, new FormField(Within.class, "wsome").getMandatory());
		assertEquals(false, new FormField(Within.class, "deep.another").getMandatory());
	}

	@Test
	public void testGetIdFieldName() {
		assertEquals("idSome", new FormField(Foo.class, "some").getIdFieldName());
		assertEquals("idSome", new FormField(Foo.class, "many").getIdFieldName());
	}

}
