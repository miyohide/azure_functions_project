package com.github.miyohide;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class HelloFunction {
    @Value("${miyohide}")
    private String miyohide;

    @Bean
    public Function<String, String> hello() {
        return info -> {
            return miyohide;
        };
    }
}
