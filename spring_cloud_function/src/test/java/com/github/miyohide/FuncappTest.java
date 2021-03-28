package com.github.miyohide;

import com.github.miyohide.model.Greeting;
import com.github.miyohide.model.User;
import com.microsoft.azure.functions.HttpRequestMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.function.context.FunctionCatalog;
import org.springframework.cloud.function.context.test.FunctionalSpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@FunctionalSpringBootTest
class FuncappTest {
    @Autowired
    private FunctionCatalog catalog;

    @Test
    public void test() {
        final String send_message = "foor bar";
        Function<Message<User>, Greeting> func = catalog.lookup("hello");
        Message<User> m = MessageBuilder.withPayload(new User(send_message))
                .setHeader("executionContext", new TestExecutionContext("aaa"))
                .build();
        Greeting g = func.apply(m);
        assertEquals(send_message + "!!!", g.getName());
    }

}