/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guru.springframework;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

/**
 *
 * @author I.T.W764
 */
@Configuration
@EnableTransactionManagement
public class H2DataSourceConfig
        implements TransactionManagementConfigurer {

    @Bean
    public DataSource dataSource() {
        DataSource data = new DataSource();
        data.setDriverClassName("org.h2.Driver");
        data.setUrl("jdbc:h2:./guru");
        data.setUsername("sa");
        data.setPassword("");
        data.setDefaultAutoCommit(true);
        data.setDefaultReadOnly(false);
        return data;
    }
//

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager();
    }

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return transactionManager();
    }

}
