package com.github.miyohide;

import com.microsoft.azure.functions.ExecutionContext;

import java.util.UUID;
import java.util.logging.Logger;

public class TestExecutionContext implements ExecutionContext {
  private final String name;

  public TestExecutionContext(String name) {
    this.name = name;
  }

  @Override
  public Logger getLogger() {
    return Logger.getLogger(TestExecutionContext.class.getName());
  }

  @Override
  public String getInvocationId() {
    return UUID.randomUUID().toString();
  }

  @Override
  public String getFunctionName() {
    return this.name;
  }
}
