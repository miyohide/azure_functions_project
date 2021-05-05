package com.github.miyohide;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.OutputBinding;
import com.microsoft.azure.functions.annotation.*;
import org.springframework.cloud.function.adapter.azure.FunctionInvoker;

public class ImageResizeHandler extends FunctionInvoker<EventSchema, Boolean> {
    @FunctionName("ImageResize")
    @StorageAccount("ImageStorage")
    public boolean imageResizeHandler(
            @EventGridTrigger(name = "event") EventSchema event,
            @BlobInput(name = "file", dataType = "binary", path = "{data.url}") byte[] content,
            @BlobOutput(name = "target", path = "myblob/{DateTime}-sample.txt")OutputBinding<String> outputItem,
            final ExecutionContext context
            ) {
        context.getLogger().info("***** EventGrid Trigger Start *****");
        context.getLogger().info("----- Blob input data size = [" + content.length + "] -----");
        handleRequest(event, context);
        context.getLogger().info("***** EventGrid Trigger End *****");
        return true;
    }
}
