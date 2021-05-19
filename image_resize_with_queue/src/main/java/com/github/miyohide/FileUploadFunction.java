package com.github.miyohide;

import com.microsoft.azure.functions.ExecutionContext;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.function.Function;

@Component("FileUploadHandler")
public class FileUploadFunction implements Function<Message<String>, String> {
  @Override
  public String apply(Message<String> m) {
    URL url;
    try {
      url = new URL(m.getPayload());
      return url.getPath().replaceFirst("^/images/", "");
    } catch (MalformedURLException e) {
      ExecutionContext context = m.getHeaders().get("executionContext", ExecutionContext.class);
      context.getLogger().severe(e.getLocalizedMessage());
      return null;
    }
  }
}
