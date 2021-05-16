package com.github.miyohide;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.OutputBinding;
import com.microsoft.azure.functions.annotation.*;
import org.springframework.cloud.function.adapter.azure.FunctionInvoker;

import java.io.IOException;

public class ImageResizeHandler extends FunctionInvoker<byte[], byte[]> {
  @FunctionName("ImageResize")
  @StorageAccount("MyStorageAccount")
  @BlobOutput(name = "target", path = "thumbnails/s-{queueTrigger}", dataType = "binary")
  public byte[] imageResizeHandler(
      @QueueTrigger(name = "msg", queueName = "myqueue", connection = "MyQueueConnection")
          String msg,
      @BlobInput(name = "file", dataType = "binary", path = "images/{queueTrigger}") byte[] file,
      final ExecutionContext context)
      throws IOException {
    context.getLogger().info("***** Queue Message = [" + msg + "] *****");
    byte[] resizeImage = ResizeImage.resize(file);
    return resizeImage;
  }
}
