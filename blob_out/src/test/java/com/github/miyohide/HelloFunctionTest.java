package com.github.miyohide;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.function.context.FunctionCatalog;
import org.springframework.cloud.function.context.test.FunctionalSpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.function.Function;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

@FunctionalSpringBootTest
class HelloFunctionTest {
    @Autowired
    private FunctionCatalog catalog;

    @Test
    public void test() {
        Pattern p = Pattern.compile("\\[\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d{6}\\] This is sample txt.");
        Function<Message<String>, String> func = catalog.lookup("hello");
        Message<String> m =
                MessageBuilder.withPayload("input")
                .setHeader("executionContext", new TestExecutionContext("testExecutionContext"))
                .build();
        String r = func.apply(m);
        assertTrue(p.matcher(r).matches());
    }
}