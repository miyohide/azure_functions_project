package com.github.miyohide;

import com.microsoft.azure.functions.ExecutionContext;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.function.Function;

@Component("ImageResize")
public class ImageResizeFunction implements Function<Message<ImageResizeFunctionArg>, byte[]> {
    @Override
    public byte[] apply(Message<ImageResizeFunctionArg> m) {
        byte[] r = null;
        ExecutionContext context = m.getHeaders().get("executionContext", ExecutionContext.class);
        ImageResizeFunctionArg arg = m.getPayload();
        EventSchema event = arg.getEventSchema();
        try {
            r = ResizeImage.resize(arg.getInput());
        } catch (IOException e) {
            context.getLogger().warning("画像変換処理に例外が発生しました。URL = [" + event.data.get("url") + "]");
        }
        return r;
    }
}
