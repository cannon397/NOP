package com.cannon.nop;

import com.cannon.nop.infrastructure.config.LoggingConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class NopApplication {
    public static void main(String[] args) {
        LoggingConfiguration.configureLogging();
        SpringApplication.run(NopApplication.class, args);
    }
}
