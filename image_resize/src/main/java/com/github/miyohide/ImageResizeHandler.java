package com.github.miyohide;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.OutputBinding;
import com.microsoft.azure.functions.annotation.BlobOutput;
import com.microsoft.azure.functions.annotation.EventGridTrigger;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.StorageAccount;
import org.springframework.cloud.function.adapter.azure.FunctionInvoker;

public class ImageResizeHandler extends FunctionInvoker<EventSchema, Boolean> {
    @FunctionName("ImageResize")
    @StorageAccount("OutputStorage")
    public boolean imageResizeHandler(
            @EventGridTrigger(name = "event") EventSchema event,
            @BlobOutput(name = "target", path = "myblob/{DateTime}-sample.txt")OutputBinding<String> outputItem,
            final ExecutionContext context
            ) {
        context.getLogger().info("***** EventGrid Trigger Start *****");
        handleRequest(event, context);
        context.getLogger().info("***** EventGrid Trigger End *****");
        return true;
    }
}
