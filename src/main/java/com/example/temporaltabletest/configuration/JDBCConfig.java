package com.example.temporaltabletest.configuration;

import com.example.temporaltabletest.EmployeeHistoryRowMapper;
import com.example.temporaltabletest.EmployeesHistory;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.QueryMappingConfiguration;
import org.springframework.data.jdbc.repository.config.DefaultQueryMappingConfiguration;

@Configuration
public class JDBCConfig {

    @Bean
    QueryMappingConfiguration rowMappers() {
        return new DefaultQueryMappingConfiguration().registerRowMapper(EmployeesHistory.class, new EmployeeHistoryRowMapper());
    }
}
