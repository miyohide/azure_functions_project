package com.github.miyohide;

import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import org.springframework.cloud.function.adapter.azure.FunctionInvoker;

public class HelloHandler extends FunctionInvoker<String, String> {
    @FunctionName("hello")
    public HttpResponseMessage hello(
            @HttpTrigger(
                    name = "req",
                    methods = {HttpMethod.GET},
                    authLevel = AuthorizationLevel.ANONYMOUS
            ) HttpRequestMessage<String> req,
            ExecutionContext context
            ) {
        context.getLogger().info("***** start http trigger *****");
        String a = handleRequest("hoge", context);
        context.getLogger().info("a = [" + a + "]");
        return req.createResponseBuilder(HttpStatus.OK).body("hello, world").header("Content-Type", "application/json").build();
    }
}
