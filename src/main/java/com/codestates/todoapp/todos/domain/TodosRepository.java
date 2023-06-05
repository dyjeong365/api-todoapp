package com.codestates.todoapp.todos.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TodosRepository extends JpaRepository<Todos, Long> {
}
