package com.github.miyohide;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = Funcapp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FuncappTest {
    @Autowired
    private TestRestTemplate rest;

    @Test
    public void test() throws URISyntaxException {
        final String sendMessage = "send message value";
        ResponseEntity<String> result = this.rest.exchange(
                RequestEntity.post(new URI("/hello")).body(sendMessage), String.class
        );
        assertEquals("{\"name\":\"Welcome, " + sendMessage + "\"}", result.getBody());
    }

}