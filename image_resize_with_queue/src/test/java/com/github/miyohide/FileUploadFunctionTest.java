package com.github.miyohide;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.function.context.FunctionCatalog;
import org.springframework.cloud.function.context.test.FunctionalSpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@FunctionalSpringBootTest
class FileUploadFunctionTest {
  @Autowired private FunctionCatalog catalog;

  @Test
  public void noSubDirTest() {
    Function<Message<String>, String> func = catalog.lookup("FileUploadHandler");
    final String filename = "abc.png";
    final String blobURL = "https://sampleblob.blob.core.windows.net/images/" + filename;
    Message<String> m =
        MessageBuilder.withPayload(blobURL)
            .setHeader("executionContext", new TestExecutionContext("test"))
            .build();
    assertEquals(filename, func.apply(m));
  }

  @Test
  public void haveSubDirTest() {
    Function<Message<String>, String> func = catalog.lookup("FileUploadHandler");
    final String filename = "subDir/abc.png";
    final String blobURL = "https://sampleblob.blob.core.windows.net/images/" + filename;
    Message<String> m =
        MessageBuilder.withPayload(blobURL)
            .setHeader("executionContext", new TestExecutionContext("test"))
            .build();
    assertEquals(filename, func.apply(m));
  }

  @Test
  public void invalidURLTest() {
    Function<Message<String>, String> func = catalog.lookup("FileUploadHandler");
    final String filename = "abc.png";
    final String blobURL = "hogehoge" + filename;
    Message<String> m =
        MessageBuilder.withPayload(blobURL)
            .setHeader("executionContext", new TestExecutionContext("test"))
            .build();
    assertNull(func.apply(m));
  }
}
