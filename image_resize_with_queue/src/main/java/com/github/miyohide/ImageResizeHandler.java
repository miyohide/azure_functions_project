package com.github.miyohide;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.QueueTrigger;
import org.springframework.cloud.function.adapter.azure.FunctionInvoker;

public class ImageResizeHandler extends FunctionInvoker<byte[], byte[]> {
  @FunctionName("ImageResize")
  public void imageResizeHandler(
      @QueueTrigger(name = "msg", queueName = "myqueue", connection = "MyQueueConnection")
          String msg,
      final ExecutionContext context) {
    context.getLogger().info("***** Queue Message = [" + msg + "] *****");
    return;
  }
}
