package com.github.miyohide;

import com.github.miyohide.model.Greeting;
import com.github.miyohide.model.User;
import com.microsoft.azure.functions.ExecutionContext;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Function;

// /api/helloで呼び出されるHTTPTriggerの処理の実装。
// @FunctionNameで指定した値と合わせる必要がある。
@Component("hello")
public class HelloFunction implements Function<Message<User>, Greeting> {
    @Override
    public Greeting apply(Message<User> m) {
        ExecutionContext context = m.getHeaders().get("executionContext", ExecutionContext.class);
        context.getLogger().info("HelloFunction.apply method");
        return new Greeting(m.getPayload().getName() + "!!!");
    }
}
