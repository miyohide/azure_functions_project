package com.github.miyohide;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.OutputBinding;
import com.microsoft.azure.functions.annotation.EventGridTrigger;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.QueueOutput;
import org.springframework.cloud.function.adapter.azure.FunctionInvoker;

public class FileUploadHandler extends FunctionInvoker<String, String> {
  @FunctionName("FileUploadHandler")
  public void pushToQueue(
      @EventGridTrigger(name = "event") EventSchema event,
      @QueueOutput(name = "queue", queueName = "${app.queueName}", connection = "MyQueueConnection")
          OutputBinding<String> queue,
      final ExecutionContext context)
      throws IllegalArgumentException {
    String filename = handleRequest(event.data.get("url").toString(), context);
    if (filename == null) {
      throw new IllegalArgumentException("Event Grid data invalid");
    } else {
      queue.setValue(filename);
    }
  }
}
