package com.github.miyohide;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component("hello")
public class HelloFunction implements Function<String, String>{
    @Value("${miyohide}")
    private String miyohide_data;

    @Override
    public String apply(String s) {
        return miyohide_data;
    }
}