package com.github.miyohide;

import com.microsoft.azure.functions.ExecutionContext;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component("ImageResize")
public class ImageResizeFunction implements Function<Message<ImageResizeFunctionArg>, Boolean> {
    @Override
    public Boolean apply(Message<ImageResizeFunctionArg> m) {
        ExecutionContext context = m.getHeaders().get("executionContext", ExecutionContext.class);
        ImageResizeFunctionArg arg = m.getPayload();
        String url = arg.getEventSchema().data.get("url").toString();
        context.getLogger().info("+++++ ImageResizeFunction url = [" + url + "] +++++");
        int size = arg.getInput().length;
        context.getLogger().info("+++++ ImageResizeFunction data size = [" + size + "] +++++");
        return true;
    }
}
