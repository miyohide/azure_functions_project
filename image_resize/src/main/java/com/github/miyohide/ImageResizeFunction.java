package com.github.miyohide;

import com.microsoft.azure.functions.ExecutionContext;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component("ImageResize")
public class ImageResizeFunction implements Function<Message<EventSchema>, Boolean> {
    @Override
    public Boolean apply(Message<EventSchema> m) {
        ExecutionContext context = m.getHeaders().get("executionContext", ExecutionContext.class);
        EventSchema event = m.getPayload();
        context.getLogger().info(event.toString());
        return true;
    }
}
