package com.avol.spring.batch.config;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by Lovababu on 6/13/2015.
 */
@Configuration
@ComponentScan(basePackages = "com.avol.spring.batch")
public class AppConfig {


    /**
     * Spring provided H2 Embedded Database.
     * It Reads the dbscript and initiates the Database.
     *
     * @return
     */

    @Bean
    public DataSource dataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        builder.setName("H2-Test-DB");
        EmbeddedDatabase db = builder.setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:dbscript/my-schema.sql")
                .addScript("classpath:dbscript/my-test-data.sql").build();
        System.out.println("Database initialization done.");
        return db;
    }
}
