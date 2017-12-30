package com.seimos.commons.reflection;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.lang.model.element.Modifier;
import javax.persistence.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.seimos.commons.controller.GenericCrudController;
import com.seimos.commons.dao.GenericDao;
import com.seimos.commons.dao.GenericDaoImpl;
import com.seimos.commons.service.GenericService;
import com.seimos.commons.service.GenericServiceImpl;
import com.seimos.commons.validator.GenericValidator;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

/**
 * @author moesio.medeiros
 * @date 29 de dez de 2017 11:51:20 
 *
 */
public class LayerGenerator {

	private static Class<?> controllerClass;
	private static Class<?> validatorClass;
	private static Class<?> serviceImplClass;
	private static Class<?> serviceClass;
	private static Class<?> daoClass;
	private static Class<?> daoImplClass;
	private static Class<?> domainClazz;
	private static ClassLoader loader = Thread.currentThread().getContextClassLoader();

	private static String getBasePackage(Class<?> clazz) {
		String packageName = clazz.getCanonicalName().substring(0, clazz.getCanonicalName().lastIndexOf("."));
		String basePackage = packageName.substring(0, packageName.lastIndexOf("."));
		return basePackage;
	}

	public static void generateLayers(Class<?> clazz) {
		domainClazz = clazz;

		if (clazz.isAnnotationPresent(Entity.class) && clazz.isAnnotationPresent(GenerateLayers.class)) {
			buildValidator(clazz);
			buildDao(clazz);
			buildDaoImpl(clazz);
			buildService(clazz);
			buildServiceImpl(clazz);
			buildController(clazz);
		}
	}

	private static void buildController(Class<?> clazz) {
		String controllerPackage = getBasePackage(clazz).concat(".controller");
		String domainName = clazz.getSimpleName();
		String controllerName = domainName.concat("Controller");

		MethodSpec setServiceMethodSpec = MethodSpec.methodBuilder("set".concat(domainName).concat("Service"))//
				.addModifiers(Modifier.PUBLIC)//
				.returns(void.class)//
				.addParameter(getServiceClass(), "service")//
				.addAnnotation(Autowired.class)//
				.addStatement("this.$N = $N", "service", "service")//
				.build();

		MethodSpec setValidatorSpec = MethodSpec.methodBuilder("set".concat(domainName).concat("Validator"))//
				.addModifiers(Modifier.PUBLIC)//
				.returns(void.class)//
				.addParameter(getValidatorClass(), "validator")//
				.addAnnotation(Autowired.class)//
				.addStatement("this.$N = $N", "validator", "validator")//
				.build();

		MethodSpec getValidatorSpec = MethodSpec.methodBuilder("getValidator")//
				.addModifiers(Modifier.PUBLIC)//
				.returns(getValidatorClass())//
				.addStatement("return $N", "validator")//
				.build();

		MethodSpec getSeviceSpec = MethodSpec.methodBuilder("getService")//
				.addModifiers(Modifier.PUBLIC)//
				.returns(getServiceClass())//
				.addStatement("return $N", "service")//
				.build();

		TypeSpec controllerSpec = TypeSpec.classBuilder(controllerName)//
				.addModifiers(Modifier.PUBLIC)//
				.superclass(ParameterizedTypeName.get(GenericCrudController.class, clazz))//
				.addAnnotation(Controller.class)//
				.addAnnotation(AnnotationSpec.builder(RequestMapping.class)//
						.addMember("path", "\"/".concat(StringUtils.uncapitalize(domainName)).concat("\""))//
						.build())//
				.addField(getServiceClass(), "service", Modifier.PRIVATE)//
				.addField(getValidatorClass(), "validator", Modifier.PRIVATE)//
				.addMethod(setServiceMethodSpec)//
				.addMethod(setValidatorSpec)//
				.addMethod(getValidatorSpec)//
				.addMethod(getSeviceSpec)//
				.build();

		controllerClass = buildClass(controllerPackage, controllerName, controllerSpec);
	}

	public static void buildValidator(Class<?> clazz) {
		String validatorPackageName = getBasePackage(clazz).concat(".validator");
		String domainName = clazz.getSimpleName();
		String validatorName = domainName.concat("Validator");

		TypeSpec validatorSpec = TypeSpec.classBuilder(validatorName)//
				.addModifiers(Modifier.PUBLIC)//
				.superclass(ParameterizedTypeName.get(GenericValidator.class, clazz))//
				.addJavadoc("Automatic generated \n" //
						+ "@author Seimos Framework\n\n" //
						+ "@date "
						+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime())//
						+ "\n")//
				.addAnnotation(Component.class)//
				.build();

		validatorClass = buildClass(validatorPackageName, validatorName, validatorSpec);
	}

	public static void buildServiceImpl(Class<?> clazz) {
		String servicePackage = getBasePackage(clazz).concat(".service");
		String domainName = clazz.getSimpleName();
		String serviceImplName = domainName.concat("ServiceImpl");

		FieldSpec fieldDaoSpec = FieldSpec.builder(getDaoClass(), "dao", Modifier.PRIVATE).build();

		MethodSpec setDaoMethodSpec = MethodSpec.methodBuilder("set".concat(domainName).concat("Dao"))//
				.addModifiers(Modifier.PUBLIC)//
				.returns(void.class)//
				.addParameter(getDaoClass(), "dao")//
				.addAnnotation(Autowired.class)//
				.addStatement("this.$N = $N", "dao", "dao")//
				.build();

		MethodSpec getDaoSpec = MethodSpec.methodBuilder("getDao")//
				.addModifiers(Modifier.PUBLIC)//
				.returns(getDaoClass())//
				.addStatement("return $N", "dao")//
				.build();

		TypeSpec serviceImpleSpec = TypeSpec.classBuilder(serviceImplName)//
				.addModifiers(Modifier.PUBLIC)//
				.superclass(ParameterizedTypeName.get(GenericServiceImpl.class, clazz, getDaoClass()))//
				.addSuperinterface(getServiceClass())//
				.addAnnotation(Service.class)//
				.addField(fieldDaoSpec)//
				.addMethod(setDaoMethodSpec)//
				.addMethod(getDaoSpec)//
				.addJavadoc("Automatic generated \n" //
						+ "@author Seimos Framework\n\n" //
						+ "@date "
						+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime())//
						+ "\n") //
				.build();

		serviceImplClass = buildClass(servicePackage, serviceImplName, serviceImpleSpec);
	}

	public static void buildService(Class<?> clazz) {
		String domainName = clazz.getSimpleName();
		String serviceName = domainName.concat("Service");

		TypeSpec serviceSpec = TypeSpec.interfaceBuilder(serviceName)//
				.addModifiers(Modifier.PUBLIC)//
				.addSuperinterface(ParameterizedTypeName.get(GenericService.class, clazz))//
				.addJavadoc("Automatic generated \n" //
						+ "@author Seimos Framework\n\n" //
						+ "@date "
						+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime())//
						+ "\n")//
				.build();

		serviceClass = buildClass(getBasePackage(clazz).concat(".service"), serviceName, serviceSpec);
	}

	public static void buildDao(Class<?> clazz) {
		String basePackage = getBasePackage(clazz);
		String daoPackageName = basePackage.concat(".dao");
		String domainName = clazz.getSimpleName();
		String daoName = domainName.concat("Dao");

		TypeSpec daoSpec = TypeSpec.interfaceBuilder(daoName)//
				.addModifiers(Modifier.PUBLIC)//
				.addSuperinterface(ParameterizedTypeName.get(GenericDao.class, clazz))//
				.addJavadoc("Automatic generated \n" //
						+ "@author Seimos Framework\n\n" //
						+ "@date "
						+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime())//
						+ "\n")//
				.build();

		daoClass = buildClass(daoPackageName, daoName, daoSpec);
	}

	public static void buildDaoImpl(Class<?> clazz) {
		String domainName = clazz.getSimpleName();
		String daoImplName = domainName.concat("DaoImpl");
		TypeSpec daoImplSpec = TypeSpec.classBuilder(daoImplName)//
				.superclass(ParameterizedTypeName.get(GenericDaoImpl.class, clazz))//
				.addSuperinterface(getDaoClass())//
				.addAnnotation(Repository.class)//
				.addJavadoc("Automatic generated \n" //
						+ "@author Seimos Framework\n\n" //
						+ "@date "
						+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime())//
						+ "\n")//
				.build();

		daoImplClass = buildClass(getBasePackage(clazz).concat(".dao"), daoImplName, daoImplSpec);
	}

	public static Class<?> buildClass(String packageName, String className, TypeSpec spec) {
		//		Runtime.getRuntime().availableProcessors()

		FileWriter writer = null;
		try {
			//			URL[] urls = ((URLClassLoader) Thread.currentThread().getContextClassLoader()).getURLs();
			//			File root = new File(urls[0].toURI());

			//			String generatedTarget = Paths.get(Thread.currentThread().getContextClassLoader().getResource("").toURI())
			//			ClassLoader loader = LayerGenerator.class.getClassLoader();
			String generatedTarget = Paths.get(loader.getResource("").toURI()).getParent().toString()
					.concat("/generated");
			File root = new File(generatedTarget);
			if (!root.exists()) {
				root.mkdirs();
			}
			File source = new File(root, packageName.replace(".", "/").concat("/").concat(className).concat(".java"));

			//			if (source.exists()) {
			//				source.delete();
			//			}

			new File(source.getParent()).mkdirs();
			writer = new FileWriter(source);
			JavaFile.builder(packageName, spec).build().writeTo(writer);
			writer.close();

			//			JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
			//			compiler.run(null, null, null, source.getPath());

			//			ClassLoader loader = LayerGenerator.class.getClassLoader();
			Class<?> daoClass = Class.forName(packageName.concat(".").concat(className), true, loader);

			return daoClass;

		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Class<?> getControllerClass() {
		if (controllerClass == null) {
			buildController(domainClazz);
		}
		return controllerClass;
	}

	public static Class<?> getValidatorClass() {
		if (validatorClass == null) {
			buildValidator(domainClazz);
		}
		return validatorClass;
	}

	public static Class<?> getServiceImplClass() {
		if (serviceImplClass == null) {
			buildServiceImpl(domainClazz);
		}
		return serviceImplClass;
	}

	public static Class<?> getServiceClass() {
		if (serviceClass == null) {
			buildService(domainClazz);
		}
		return serviceClass;
	}

	public static Class<?> getDaoClass() {
		if (daoClass == null) {
			buildDao(domainClazz);
		}
		return daoClass;
	}

	public static Class<?> getDaoImplClass() {
		if (daoImplClass == null) {
			buildDaoImpl(domainClazz);
		}
		return daoImplClass;
	}

}
