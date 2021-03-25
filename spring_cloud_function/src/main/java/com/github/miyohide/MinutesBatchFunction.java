package com.github.miyohide;

import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component("minutesBatch")
public class MinutesBatchFunction implements Function<String, String> {
    @Override
    public String apply(String s) {
        return null;
    }
}
