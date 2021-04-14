package com.github.miyohide.repository;

import com.github.miyohide.domain.Todo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJdbcTest
public class TodoRepositoryTest {
    @Autowired
    TodoRepository todoRepository;

    @Test
    void testCrudMethods() {
        Todo t = new Todo("test todo");
        assertEquals(0, todoRepository.count());
        todoRepository.save(t);
        assertEquals(1, todoRepository.count());
    }
}
