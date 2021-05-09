package com.github.miyohide;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.OutputBinding;
import com.microsoft.azure.functions.annotation.*;
import org.springframework.cloud.function.adapter.azure.FunctionInvoker;

public class ImageResizeHandler extends FunctionInvoker<ImageResizeFunctionArg, byte[]> {
    @FunctionName("ImageResize")
    @StorageAccount("ImageStorage")
    public boolean imageResizeHandler(
            @EventGridTrigger(name = "event") EventSchema event,
            @BlobInput(name = "file", dataType = "binary", path = "{data.url}") byte[] content,
            @BlobOutput(name = "target", path = "myblob/{DateTime}-sample.png")OutputBinding<byte[]> outputItem,
            final ExecutionContext context
            ) {
        context.getLogger().info("***** EventGrid Trigger Start *****");
        if (content == null) {
            context.getLogger().info("content is null");
        } else {
            context.getLogger().info("***** [" + content.length + "]");
        }
        ImageResizeFunctionArg arg = new ImageResizeFunctionArg(event, content);
        byte[] out = handleRequest(arg, context);
        outputItem.setValue(out);
        context.getLogger().info("***** EventGrid Trigger End *****");
        return true;
    }
}
