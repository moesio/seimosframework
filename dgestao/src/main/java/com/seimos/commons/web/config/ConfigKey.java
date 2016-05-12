package com.seimos.commons.web.config;

/**
 * @author moesio @ gmail.com
 * @date Sep 22, 2014 10:13:28 PM
 */
public enum ConfigKey {
	config_package {
		@Override
		public String getComment() {
			return "Package where project configuration class stands\n" //
					+ "Example: \n " + //
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

	datasource_jdbc_driverClassName {
		@Override
		public String getComment() {
			return "Full package qualified named driver class name";
		}
	},
	datasource_jdbc_url {
		@Override
		public String getComment() {
			return "Jdbc driver URL connection";
		}
	},
	datasource_jdbc_username {
		@Override
		public String getComment() {
			return "Jdbc username for connection";
		}
	},
	datasource_jdbc_password {
		@Override
		public String getComment() {
			return "Jdbc password for connection";
		}
	},
	datasource_packageToScan {
		@Override
		public String getComment() {
			return "Full qualified package where entities are";
		}
	},
	datasource_initScript {
		/* (non-Javadoc)
		 * @see br.com.seimos.commons.web.config.Keys#getComment()
		 */
		/* (non-Javadoc)
		 * @see br.com.seimos.commons.web.config.Keys#getComment()
		 */
		@Override
		public String getComment() {
			return "File path in classpath to be used for database initializing while application starts";
		}
	},
	hibernate_hbm2ddl_auto {
		@Override
		public String getComment() {
			return "What to do to database in relation to entities mapped in \"" + datasource_packageToScan + "\" when application starts\n" + //
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
	};

	/**
	 * Shows explanation about key 
	 * 
	 * @return
	 */
	public abstract String getComment();

	public String toString() {
		return this.name().replace("_", ".");
	}
};