package com.springbootjpaangular2.configuration;


/*import java.util.HashMap;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;*/

import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.google.common.base.Preconditions;


/** 
 * @author lyi
 * 08/2023
 * For JpaRepository of Springboot application, we only need to provider application.properties configuration file and 
 * not necessary to provide any methods implementation of this class.
 * comment out all methods of this class, it still worked.
 * And not need persistence.xml in META-INF folder.
 * 
 * it is OK to use persistence.xml without adding properties in application.properties.
 * 
 * Hibernate is a JPA implementation, while Spring Data JPA is a JPA Data Access Abstraction(implemented as repositories). 
 * Spring Data JPA is not a JPA provider. It is a library/framework that adds an extra layer 
 * of abstraction on the top of our JPA provider (like Hibernate).
 * 
 * Spring Data JPA allow us to define dao interfaces by extending its repositories(crudrepository, 
 * jparepository) so it provide you dao implementation at runtime. 
 * You don't need to write dao implementation anymore.
 */
@Configuration  
@EnableTransactionManagement
//@PropertySource("classpath:application.properties")                 // automatically load default property
//@EnableJpaRepositories(basePackages = "com.springbootjpaangular2")  // automatically detected repositories
//@EntityScan(basePackages = {"com.springbootjpaangular2.domain"})    // automatically detected repositories
public class DBConfigurationProperties {
	
	
	   public DBConfigurationProperties() { super(); }

	   @Autowired
	   private Environment env;

	   
	    /**
	    @Bean("entityManagerFactory")
	    public LocalSessionFactoryBean sessionFactory( ) {
	        LocalSessionFactoryBean o = new LocalSessionFactoryBean();
	        o.setDataSource(dataSource());
	        o.setPackagesToScan("com.springbootjpaangular2.domain");
	        Properties props = new Properties();
	        props.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
	        props.setProperty("hibernate.show_sql", "true");
	        props.setProperty("hibernate.format_sql", "true");
	        props.setProperty("hibernate.hbm2ddl.auto", "create-drop");
	        props.setProperty("hibernate.hbm2ddl.import_files", "classpath:/data.sql");  //classpath:/schema.sql,
	        o.setHibernateProperties(props);
	        return o;
	    }
	    **/
	    
	    /**
	     * return either LocalContainerEntityManagerFactoryBean or EntityManagerFactory is OK.
	     * but must have em.afterPropertiesSet() to create table schema if return em.getObject()/EntityManagerFactory
	     * @return
	     */
	   
	    @Bean
	    public EntityManagerFactory entityManagerFactory() {
	        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
	        em.setDataSource(dataSource());
	        em.setPackagesToScan("com.springbootjpaangular2.domain");

	        /**
	         * an alternative to the way we specify the hibernate configurations
	         * final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();	     
	         * em.setJpaVendorAdapter(vendorAdapter); 
	         * 
	         * instead we can use: em.setPersistenceProviderClass(HibernatePersistenceProvider.class); 
	         * to specify property of provider
	         */    
	        em.setPersistenceProviderClass(HibernatePersistenceProvider.class);
	        /**
	          em.setJpaPropertyMap(new HashMap<String, Object>() {{
	            put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
	            put("hibernate.hbm2ddl.auto","create-drop");
	            put("hibernate.show_sql", "true");
	            put("hibernate.cache.use_second_level_cache" ,"false");
	            put("hibernate.enable_lazy_load_no_trans", "true");
	            //put("hibernate.ddl-auto", "create-drop");
	            //put("javax.persistence.sql-load-script-source" ,"data.sql");
	            put("hibernate.hbm2ddl.import_files", "import.sql");
	          }});
	          
	          instead we can use em.setJpaProperties(additionalProperties());
	          **/
	        em.setJpaProperties(additionalProperties());
	        em.afterPropertiesSet(); // no need this if return LocalContainerEntityManagerFactoryBean
	        return  em.getObject();  
	    }
	    
	    @Bean
	    public DataSource dataSource() {
	    	// spring.datasource.url=jdbc:h2:mem:testdb;MODE=Oracle;DB_CLOSE_ON_EXIT=FALSE;DB_CLOSE_DELAY=-1
	    	// without DB_CLOSE_DELAY=-1, the schema created (but lost) and when run application, the error "QUOTES" table not found
	        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
	        dataSource.setDriverClassName(Preconditions.checkNotNull(env.getProperty("spring.datasource.driver-class-name")));
	        dataSource.setUrl(Preconditions.checkNotNull(env.getProperty("spring.datasource.url")));
	        dataSource.setUsername(Preconditions.checkNotNull(env.getProperty("spring.datasource.username")));
	        dataSource.setPassword(Preconditions.checkNotNull(env.getProperty("spring.datasource.password")));

	        return dataSource;
	    }

	    @Bean
	    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) { // EntityManagerFactory emf
	        final JpaTransactionManager transactionManager = new JpaTransactionManager();
	        transactionManager.setEntityManagerFactory(emf); // entityManagerFactory().getObject()
	        return transactionManager;
	    }

	    @Bean
	    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
	        return new PersistenceExceptionTranslationPostProcessor();
	    }
	    
	    /**
	     * 1. check Spring properties: https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html#appendix.application-properties.data
	     * 2. check JPA properties such as  Javax JPA 2.0, Jakarta JPA (Javax JPA renamed)
	     * 3. check JPA provider/implement properties such as:  Hibernate
	     * 4. check DB (Oracle, PostgreSql, SQL Server,...) URL connection properties such as H2 DB: http://www.h2database.com/html/features.html
	     * 
	     * same meaning properties may cause initializing duplicated records such as:
	     * "spring.jpa.hibernate.ddl-auto"/"hibernate.hbm2ddl.auto"
	     * "hibernate.hbm2ddl.import_files"/"spring.jpa.properties.javax.persistence.sql-load-script"
	     * 
	     * @return
	     */
	    final Properties additionalProperties() {
	        final Properties springJPAProperties = new Properties();
	        springJPAProperties.setProperty("hibernate.dialect", env.getProperty("spring.jpa.hibernate.dialect"));
	        springJPAProperties.setProperty("spring.jpa.defer-datasource-initialization", env.getProperty("spring.jpa.defer-datasource-initialization"));
	        springJPAProperties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.hbm2ddl.auto"));
	        springJPAProperties.setProperty("hibernate.cache.use_second_level_cache", "false");        
	        springJPAProperties.setProperty("hibernate.hbm2ddl.import_files", env.getProperty("spring.jpa.properties.hibernate.hbm2ddl.import_files"));
	        springJPAProperties.setProperty("spring.jpa.show_sql", env.getProperty("spring.jpa.properties.hibernate.show_sql"));
	        springJPAProperties.setProperty("hibernate.enable_lazy_load_no_trans", env.getProperty("spring.jpa.properties.hibernate.enable_lazy_load_no_trans"));
	        return springJPAProperties;
	    }
	   
}
