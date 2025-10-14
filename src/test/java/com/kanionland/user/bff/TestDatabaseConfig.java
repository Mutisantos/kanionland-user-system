package com.kanionland.user.bff;


import javax.sql.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestDatabaseConfig {

  public static final String H2_DEFAULT_PASSWORD = "password";
  public static final String H2_DEFAULT_USERNAME = "sa";

  @Bean
  @Primary
  public DataSource dataSource() {
    return DataSourceBuilder.create()
        .driverClassName("org.h2.Driver")
        .url("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE")
        .username(H2_DEFAULT_USERNAME)
        .password(H2_DEFAULT_PASSWORD)
        .build();
  }
}