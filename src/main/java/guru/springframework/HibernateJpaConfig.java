package guru.springframework;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
* @author I.T.W764
 */
@Configuration
@EnableJpaRepositories(basePackages = {
    "guru.springframework.repositories"
})
public class HibernateJpaConfig {

    private final Map<String, String> PROPERTIES = new HashMap<>();

    public HibernateJpaConfig() {

        PROPERTIES.put("hibernate.hbm2ddl.auto", "create-drop");
        PROPERTIES.put("hibernate.ejb.naming.strategy", "org.hibernate.cfg.DefaultComponentSafeNamingStrategy");

        System.out.println("--- HibernateJpaConfig()");
    }

    @Autowired
    private DataSource dataSource;

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        AbstractJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setShowSql(true);
        adapter.setDatabase(Database.H2);
        adapter.setDatabasePlatform("H2");
        adapter.setGenerateDdl(true);
        return adapter;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("guru.springframework.domain");

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaPropertyMap(PROPERTIES);

        return em;

    }
}
