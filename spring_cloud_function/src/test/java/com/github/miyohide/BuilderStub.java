// テスト用クラス。Spring Cloud Functionのテストコードから。
// https://github.com/spring-cloud/spring-cloud-function/blob/5a5ed0841f39412b8a751be77fba818271af5a9f/spring-cloud-function-adapters/spring-cloud-function-adapter-azure/src/test/java/org/springframework/cloud/function/adapter/azure/HttpFunctionInvokerTests.java
package com.github.miyohide;

import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatusType;

import java.util.HashMap;
import java.util.Map;

public class BuilderStub implements HttpResponseMessage.Builder {
    private HttpStatusType status;
    private Map<String, String> headers = new HashMap<>();
    private Object body;

    @Override
    public HttpResponseMessage.Builder status(HttpStatusType status) {
        this.status = status;
        return this;
    }

    @Override
    public HttpResponseMessage.Builder header(String key, String value) {
        this.headers.put(key, value);
        return this;
    }

    @Override
    public HttpResponseMessage.Builder body(Object body) {
        this.body = body;
        return this;
    }

    @Override
    public HttpResponseMessage build() {
        return new HttpResponseMessageStub(this.status, this.headers, this.body);
    }
}
