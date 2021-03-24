package com.github.miyohide;

import org.springframework.stereotype.Component;

import java.util.function.Function;

// /api/helloで呼び出されるHTTPTriggerの処理の実装。
// @FunctionNameで指定した値と合わせる必要がある。
@Component("hello")
public class HelloFunction implements Function<User, Greeting> {
    @Override
    public Greeting apply(User user) {
        return new Greeting(user.getName() + "!!!");
    }
}
