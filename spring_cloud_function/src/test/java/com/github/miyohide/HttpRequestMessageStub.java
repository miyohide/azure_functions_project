// テスト用クラス。Spring Cloud Functionのテストコードから。
// https://github.com/spring-cloud/spring-cloud-function/blob/5a5ed0841f39412b8a751be77fba818271af5a9f/spring-cloud-function-adapters/spring-cloud-function-adapter-azure/src/test/java/org/springframework/cloud/function/adapter/azure/HttpFunctionInvokerTests.java
package com.github.miyohide;

import com.microsoft.azure.functions.*;

import java.net.URI;
import java.util.Map;

public class HttpRequestMessageStub<I> implements HttpRequestMessage<I> {
    private URI uri;
    private HttpMethod httpMethod;
    private Map<String, String> headers;
    private Map<String, String> queryParameters;
    private I body;

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void setQueryParameters(Map<String, String> queryParameters) {
        this.queryParameters = queryParameters;
    }

    public void setBody(I body) {
        this.body = body;
    }

    @Override
    public URI getUri() {
        return this.uri;
    }

    @Override
    public HttpMethod getHttpMethod() {
        return this.httpMethod;
    }

    @Override
    public Map<String, String> getHeaders() {
        return this.headers;
    }

    @Override
    public Map<String, String> getQueryParameters() {
        return this.queryParameters;
    }

    @Override
    public I getBody() {
        return this.body;
    }

    @Override
    public HttpResponseMessage.Builder createResponseBuilder(HttpStatus status) {
        return new BuilderStub().status(status);
    }

    @Override
    public HttpResponseMessage.Builder createResponseBuilder(HttpStatusType status) {
        return new BuilderStub().status(status);
    }
}
