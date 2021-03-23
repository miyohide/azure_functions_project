package com.github.miyohide;

import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class HelloFunction implements Function<User, Greeting> {
    @Override
    public Greeting apply(User user) {
        return new Greeting(user.getName() + "!!!");
    }
}
