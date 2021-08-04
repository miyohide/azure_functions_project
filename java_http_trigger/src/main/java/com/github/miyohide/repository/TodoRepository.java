package com.github.miyohide.repository;

import com.github.miyohide.domain.Todo;
import org.springframework.data.repository.CrudRepository;

public interface TodoRepository extends CrudRepository<Todo, Integer> {}
