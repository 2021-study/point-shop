package product.demo.shop.configuration;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import product.demo.shop.util.CryptoConvertUtil;

// https://www.baeldung.com/spring-boot-configure-data-source-programmatic
@Configuration
@EnableTransactionManagement
public class DataBaseConfiguration {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String encryptedUsername;

    @Value("${spring.datasource.password}")
    private String encryptedPassword;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Bean
    public DataSource defaultDataSource(){
        var dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url(url);
        dataSourceBuilder.username(CryptoConvertUtil.decrypt(encryptedUsername));
        dataSourceBuilder.password(CryptoConvertUtil.decrypt(encryptedPassword));
        dataSourceBuilder.driverClassName(driverClassName);
        return dataSourceBuilder.build();
    }

//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
//        var em = new LocalContainerEntityManagerFactoryBean();
//        em.setDataSource(defaultDataSource());
//        em.setPackagesToScan("product.demo.shop");
//        return em;
//    }
//
//    @Bean
//    public PlatformTransactionManager transactionManager() {
//        var transactionManager = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
//        return transactionManager;
//    }
}
