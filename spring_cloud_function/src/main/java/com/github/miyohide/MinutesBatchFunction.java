package com.github.miyohide;

import com.microsoft.azure.functions.ExecutionContext;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component("minutesBatch")
public class MinutesBatchFunction implements Function<Message<String>, String> {
    @Override
    public String apply(Message<String> m) {
        ExecutionContext context = m.getHeaders().get("executionContext", ExecutionContext.class);
        context.getLogger().info("start minutes batch");
        context.getLogger().info("end minutes batch");
        return "SUCCESS";
    }
}
