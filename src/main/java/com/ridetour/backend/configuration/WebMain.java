package com.ridetour.backend.configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.kaczmarzyk.spring.data.jpa.web.SpecificationArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.MetricFilterAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.solr.SolrAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.sql.DataSource;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.builders.PathSelectors.regex;

/**
 * Created by eyal on 5/21/2016.
 */
@Configuration
@EnableAutoConfiguration(exclude = {MetricFilterAutoConfiguration.class, SolrAutoConfiguration.class})
@EnableSwagger2
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.ridetour.backend"})
@EnableJpaRepositories(basePackages = {"com.ridetour.backend"})
public class WebMain extends WebMvcConfigurerAdapter implements WebApplicationInitializer, TransactionManagementConfigurer {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(WebMain.class, args);
    }

    private ApiKey apiKey() {
        return new ApiKey("mykey", "api_key", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.ant("/*"))
                .build();
    }

    @Bean
    public Docket tourApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("tour-api")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ridetour.backend.controllers"))
                .paths(regex("/tour.*"))
                .build()
                .useDefaultResponseMessages(false);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("RideTour services API")
                .description("RideTour services API")
                .license("Apache License Version 2.0")
                .licenseUrl("https://ridetour.com/license")
                .version("1.0.0")
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return newArrayList(
                new SecurityReference("mykey", authorizationScopes));
    }

    @Bean
    SecurityConfiguration security() {
        return new SecurityConfiguration(
                "abc",
                "123",
                "test-app-realm",
                "test-app",
                "apiKey",
                ApiKeyVehicle.HEADER,
                "api_key",
                "," /*scope separator*/);
    }


    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new SpecificationArgumentResolver());
        argumentResolvers.add(new PageableHandlerMethodArgumentResolver());
    }

    // Amazon Configuration
    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.region}")
    private String region;

    @Bean
    public BasicAWSCredentials basicAWSCredentials() {
        return new BasicAWSCredentials(accessKey, secretKey);
    }

    @Bean
    public AmazonS3Client amazonS3Client(AWSCredentials awsCredentials) {
        //amazonS3Client.setRegion(Region.getRegion(Regions.fromName(region)));
        return new AmazonS3Client(awsCredentials);
    }

    //
    @Autowired
    Environment env;

    @Bean // connection pool
    @Primary
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(env.getRequiredProperty("spring.datasource.driverClassName"));
        config.setJdbcUrl(env.getRequiredProperty("spring.datasource.url"));
        config.setUsername(env.getProperty("spring.datasource.username"));
        config.setPassword(env.getProperty("spring.datasource.password"));
        config.setConnectionTimeout(env.getProperty("spring.datasource.connectionTimeoutMillis", Long.class, TimeUnit.SECONDS.toMillis(30)));
        config.setIdleTimeout(env.getProperty("spring.datasource.idleTimeoutMillis",
                Long.class, TimeUnit.MINUTES.toMillis(5)));
        config.setMaxLifetime(env.getProperty("spring.datasource.maxLifetimeMillis",
                Long.class, TimeUnit.MINUTES.toMillis(10)));
        config.setMaximumPoolSize(env.getProperty("spring.datasource.maxPoolSize",
                Integer.class, 10));

        return new HikariDataSource(config);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factoryBean =
                new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setPackagesToScan(env.getRequiredProperty("repositories.scan"));
        String ddl = env.getProperty("spring.jpa.hibernate.ddlAuto", "none");
        // vendor configuration
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(env.getProperty("spring.jpa.showSql", Boolean.class, false));
        vendorAdapter.setGenerateDdl(!ddl.equals("none"));
        //vendorAdapter.setDatabasePlatform(Strings.nullToEmpty(env.getProperty("spring.jpa.databasePlatform")));
        vendorAdapter.setDatabase(env.getProperty("spring.jpa.database",
                Database.class, Database.DEFAULT));
        factoryBean.setJpaVendorAdapter(vendorAdapter);
        // jpa properties
        Properties additionalProperties = new Properties();
        additionalProperties.put("hibernate.hbm2ddl.auto", ddl);
        additionalProperties.put("hibernate.jdbc.fetch_size", 50);
        additionalProperties.put("hibernate.jdbc.batch_size", 100);
        // additionalProperties.put("hibernate.ejb.naming_strategy" ,
        //        env.getProperty("spring.jpa.hibernate.namingStrategy"));
        factoryBean.setJpaProperties(additionalProperties);
        return factoryBean;
    }


    @Bean
    @Primary
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return transactionManager();
    }

    @Bean(name = "mysqlJdbcTemplate")
    public NamedParameterJdbcTemplate mysqlNamedParameterJdbcTemplate(DataSource dataSource)
            throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    @Primary
    public TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager) {
        return new TransactionTemplate(transactionManager);
    }

}
