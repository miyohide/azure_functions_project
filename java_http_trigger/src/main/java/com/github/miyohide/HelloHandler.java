package com.github.miyohide;

import com.github.miyohide.model.Greeting;
import com.github.miyohide.model.User;
import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import org.springframework.cloud.function.adapter.azure.FunctionInvoker;

import java.util.Optional;

public class HelloHandler extends FunctionInvoker<User, Greeting> {
  // FunctionNameで指定しているBeanを探して、handleRequestメソッド呼び出し時に
  // 対象のメソッドを呼び出す。HTTPTriggerの場合は、URLの一部にもなる。
  @FunctionName("hello")
  public HttpResponseMessage hello(
      @HttpTrigger(
              name = "request",
              methods = {HttpMethod.GET, HttpMethod.POST},
              authLevel = AuthorizationLevel.ANONYMOUS)
          HttpRequestMessage<Optional<User>> request,
      ExecutionContext context) {
    context.getLogger().info("Java HTTP trigger processed a request.");

    // リクエストのBodyにnameデータがある場合はそのデータを返す、
    // なければクエリパラメータでnameデータがある場合はそれを使ってUserを作って返す、
    // それもなければWorldを値としてUserを使って返す
    User user =
        request
            .getBody()
            .filter((u -> u.getName() != null))
            .orElseGet(() -> new User(request.getQueryParameters().getOrDefault("name", "World")));
    return request
        .createResponseBuilder(HttpStatus.OK)
        .body(handleRequest(user, context))
        .header("Content-Type", "application/json")
        .build();
  }
}
