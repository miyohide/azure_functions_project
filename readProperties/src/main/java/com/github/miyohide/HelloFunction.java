package com.github.miyohide;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component("hello")
public class HelloFunction implements Function<String, String>{
    @Value("${myapp.name}")
    private String myapp_name;

    @Value("${myapp.hoge}")
    private String myapp_hoge;

    @Override
    public String apply(String s) {
        return myapp_name + " " + myapp_hoge;
    }
}