package com.github.miyohide;

import com.microsoft.azure.functions.ExecutionContext;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.function.Function;

@Component("hello")
public class HelloFunction implements Function<Message<String>, String> {
    @Override
    public String apply(Message<String> m) {
        ExecutionContext context = m.getHeaders().get("executionContext", ExecutionContext.class);
        String s = m.getPayload();
        context.getLogger().info("===== HelloFunction.apply method start =====");
        context.getLogger().info("===== payload info = [" + s + "] =====" );
        context.getLogger().info("===== HelloFunction.apply method end =====");
        return "[" + LocalDateTime.now() + "] This is sample txt.";
    }
}
