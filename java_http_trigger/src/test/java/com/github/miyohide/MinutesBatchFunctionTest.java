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

@ExtendWith(SpringExtension.class)
@FunctionalSpringBootTest
public class MinutesBatchFunctionTest {
  @Autowired private FunctionCatalog catalog;

  @Test
  public void test() {
    Function<Message<String>, String> func = catalog.lookup("minutesBatch");
    Message<String> m =
        MessageBuilder.withPayload("xxxxxx")
            .setHeader("executionContext", new TestExecutionContext("aaa"))
            .build();
    String s = func.apply(m);
    assertEquals("SUCCESS", s);
  }
}
