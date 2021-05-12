package com.github.miyohide;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.OutputBinding;
import com.microsoft.azure.functions.annotation.EventGridTrigger;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.QueueOutput;
import org.springframework.cloud.function.adapter.azure.FunctionInvoker;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;

public class FileUploadHandler extends FunctionInvoker<EventSchema, String> {
  @FunctionName("FileUploadHandler")
  public void pushToQueue(
      @EventGridTrigger(name = "event") EventSchema event,
      @QueueOutput(name = "queue", queueName = "myqueue", connection = "MyQueueConnection")
          OutputBinding<String> queue,
      final ExecutionContext context)
      throws MalformedURLException {
    try {
      URL url = new URL(event.data.get("url").toString());
      String filename = Paths.get(url.getPath()).getFileName().toString();
      queue.setValue(filename);
    } catch (MalformedURLException e) {
      context.getLogger().severe(e.getLocalizedMessage());
      throw e;
    }
  }
}
