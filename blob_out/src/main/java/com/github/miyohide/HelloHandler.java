package com.github.miyohide;

import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import org.springframework.cloud.function.adapter.azure.FunctionInvoker;

public class HelloHandler extends FunctionInvoker<String, String> {
    @FunctionName("hello")
    public HttpResponseMessage hello(
            @HttpTrigger(name = "req", methods = {HttpMethod.GET}, authLevel = AuthorizationLevel.ANONYMOUS)HttpRequestMessage<String> request,
            ExecutionContext context
            ) {
        context.getLogger().info("***** HTTP Trigger Start *****");
        context.getLogger().info("***** HTTP Trigger End *****");
        return request.createResponseBuilder(HttpStatus.OK)
                .body("***** HTTP Trigger Response *****")
                .header("Content-Type", "application/json")
                .build();
    }
}
