package com.kanionland.user.bff.infrastructure.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import org.springframework.core.env.Environment;

@Configuration
public class DatabaseConfig {

  @Bean
  @Profile("local")
  public DataSource dataSourceLocal(Environment env) {
    return DataSourceBuilder.create()
        .driverClassName("org.sqlite.JDBC")
        .url("jdbc:sqlite:userdb.sqlite")
        .build();
  }

  @Bean
  @Profile("!local & !test")
  public DataSource dataSource(Environment env) {
    return DataSourceBuilder.create().build();
  }

}
