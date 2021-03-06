package com.seimos.commons.web.config;

/**
 * @author moesio @ gmail.com
 * @date Sep 22, 2014 10:13:28 PM
 */
public enum ConfigKey {
	configPackage {
		@Override
		public String getComment() {
			return "Comma separated packages where project configuration classes stand.\n" + //
			"Every class must be unique name.\n " + //
			"Example: \n " + //
			"import org.springframework.context.annotation.ComponentScan;\n" + // 
			"import org.springframework.context.annotation.Configuration;\n" + //
			"\n" + //
			"@Configuration\n" + //
			"@ComponentScan(basePackages = {\n" + //
			"		\"com.seimos.example.controller\",\n" + // 
			"		\"com.seimos.example.service\", \n" + //
			"		\"com.seimos.example.dao\", \n" + //
			"		\"com.seimos.example.validator\", \n" + //
			"		})\n" + //
			"public class WebConfig {\n" + //
			"			\n" + //
			"}\n";
		}
	},
	datasource_jndi_name {
		@Override
		public String getComment() {
			return "Optional jndi datasource name in context.xml";
		}
	},
	datasource_packageToScan {
		@Override
		public String getComment() {
			return "Full qualified package where entities are";
		}
	},
	datasource_initScript {
		@Override
		public String getComment() {
			return "File path in classpath to be used for database initializing while application starts";
		}
	},
	hibernate_hbm2ddl_auto {
		@Override
		public String getComment() {
			return "What to do to database in relation to entities mapped in \"" + datasource_packageToScan
					+ "\" when application starts\n" + //
			"It can be \n" + //
			" validate: validate the schema, makes no changes to the database.\n" + //
			" update: update the schema.\n" + //
			" create: creates the schema, destroying previous data.\n" + //
			" create-drop: drop the schema at the end of the session.\n ";
		}
	},
	hibernate_dialect {
		@Override
		public String getComment() {
			return "Database dialect";
		}
	},
	templatePathLoaders {
		@Override
		public String getComment() {
			return "Comma separated places where views stands for being rendered";
		}
	},
	decorator {
		@Override
		public String getComment() {
			return "Full path for sitemesh decorator";
		}
	},
	displayName {
		@Override
		public String getComment() {
			return "Display name for context server";
		}
	},
	viewPath {
		@Override
		public String getComment() {
			return "Path to views, e.g., \"/WEB-INF/views/\"";
		}
	},
	dateFormat {
		@Override
		public String getComment() {
			return "Date format";
		}
	};

	/**
	 * Shows explanation about key
	 * 
	 * @return
	 */
	public abstract String getComment();

	@Override
	public String toString() {
		return this.name().replace("_", ".");
	}
};