// テスト用クラス。Spring Cloud Functionのテストコードから。
// https://github.com/spring-cloud/spring-cloud-function/blob/5a5ed0841f39412b8a751be77fba818271af5a9f/spring-cloud-function-adapters/spring-cloud-function-adapter-azure/src/test/java/org/springframework/cloud/function/adapter/azure/HttpFunctionInvokerTests.java
package com.github.miyohide;

import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatusType;

import java.util.HashMap;
import java.util.Map;

public class HttpResponseMessageStub implements HttpResponseMessage {
    private HttpStatusType status;
    private Map<String, String> headers = new HashMap<>();
    private Object body;

    public HttpResponseMessageStub(HttpStatusType status, Map<String, String> headers, Object body) {
        this.status = status;
        this.headers = headers;
        this.body = body;
    }

    @Override
    public HttpStatusType getStatus() {
        return this.status;
    }

    @Override
    public String getHeader(String key) {
        return this.headers.get(key);
    }

    @Override
    public Object getBody() {
        return this.body;
    }
}
