package com.github.miyohide;

import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.*;
import org.springframework.cloud.function.adapter.azure.FunctionInvoker;

import java.time.LocalDateTime;

public class HelloHandler extends FunctionInvoker<String, String> {
    @FunctionName("hello")
    @StorageAccount("OutputStorage")
    public HttpResponseMessage hello(
            @HttpTrigger(name = "req", methods = {HttpMethod.GET}, authLevel = AuthorizationLevel.FUNCTION)HttpRequestMessage<String> request,
            @BlobOutput(name = "target", path = "myblob/{DateTime:yyyy}/{DateTime:MM}/{DateTime:dd}/{DateTime:hhmmss}-{rand-guid}-sample.txt") OutputBinding<String> outputItem,
            ExecutionContext context
            ) {
        context.getLogger().info("***** HTTP Trigger Start *****");
        outputItem.setValue(handleRequest("input", context));
        context.getLogger().info("***** HTTP Trigger End *****");
        return request.createResponseBuilder(HttpStatus.OK)
                .body("***** HTTP Trigger Response *****")
                .header("Content-Type", "application/json")
                .build();
    }
}
