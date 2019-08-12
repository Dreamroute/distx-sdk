package com.github.dreamroute.mq.sdk.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.dreamroute.sqlprinter.interceptor.SqlPrinter;

/**
 * 
 * @author w.dehai
 *
 */
@Configuration
public class SqlPrinterConfig {

    @Value("${sqlType:error}")
    private String sqlType;

    /**
     * SQL打印
     */
    @Bean
    public SqlPrinter printer() {
        SqlPrinter printer = new SqlPrinter();
        Properties props = new Properties();
        props.setProperty("type", sqlType);
        printer.setProperties(props);
        return printer;
    }

}
