package com.github.miyohide.repository;

import com.github.miyohide.domain.Todo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJdbcTest
@AutoConfigureTestDatabase(replace = NONE)
public class TodoRepositoryTest {
    @Autowired
    TodoRepository todoRepository;

    @Test
    void testCrudMethods() {
        String todo_title = "test todo";
        Todo t = new Todo(todo_title);
        assertEquals(1, todoRepository.count());
        Todo savedTodo = todoRepository.save(t);
        assertEquals(2, todoRepository.count());
        assertEquals(todo_title, savedTodo.getTitle());
    }
}
