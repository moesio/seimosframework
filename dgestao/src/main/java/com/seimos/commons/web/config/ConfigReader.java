package com.seimos.commons.web.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import com.seimos.commons.web.exception.ConfigKeyExcpectedException;

/**
 * @author Moesio Medeiros
 * @date May 2, 2014 11:22:15 PM
 * 
 */
public class ConfigReader {

	/**
	 * Resource for avoid module configuration hard coding 
	 */
	private static final String CONFIG_PROPERTIES_FILE_NAME = "config.properties";
	private static Properties configProperties;
	public static File configResource = new File(ConfigReader.class.getResource("/").getPath() + CONFIG_PROPERTIES_FILE_NAME);

	/**
	 * @param key
	 * @return
	 */
	public static String getKey(ConfigKey key) {
		String value = (String) getInstance().get(key.toString());
		if (value == null) {
			throw new ConfigKeyExcpectedException(CONFIG_PROPERTIES_FILE_NAME + " has not '" + key + "' key which is for\n" + key.getComment());
		}
		return value;
	}

	/**
	 * @param key
	 * @return
	 */
	public static String get(String key) {
		return (String) getInstance().get(key);
	}

	private static Properties getInstance() {
		if (configProperties == null) {
			try {
				configProperties = new Properties();
				FileReader reader = new FileReader(configResource);
				configProperties.load(reader);
			} catch (FileNotFoundException e) {
				throw new RuntimeException(CONFIG_PROPERTIES_FILE_NAME + " not found at classpath");
			} catch (IOException e) {
				throw new RuntimeException(CONFIG_PROPERTIES_FILE_NAME + " cannot be loaded");
			}
		}
		return configProperties;
	}

	//	/**
	//	 * @throws IOException 
	//	 * 
	//	 */
	//	private static void createConfigFile() {
	//		try {
	//			Writer writer = new FileWriter(configResource);
	//			configProperties.store(writer, "");
	//		} catch (IOException e) {
	//			throw new RuntimeException(CONFIG_PROPERTIES_FILE_NAME + " can't be written");
	//		}
	//	}

}
