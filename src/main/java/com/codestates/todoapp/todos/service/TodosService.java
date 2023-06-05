package com.codestates.todoapp.todos.service;

import com.codestates.todoapp.todos.TodosDto;
import com.codestates.todoapp.todos.domain.Todos;
import com.codestates.todoapp.todos.domain.TodosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class TodosService {

    private final TodosRepository todosRepository;


    public Todos createTodo(Todos todos) {
        return todosRepository.save(todos);
    }

    @Transactional(readOnly = true)
    public List<Todos> readTodos() {
        return todosRepository.findAll();
    }

    public Todos readTodo(long id) {
        return findVerifiedTodos(id);
    }


    public Todos updateTodo(long id, Todos todos) {
        Todos findTodos = findVerifiedTodos(id);

        if(todos.getTodoOrder() != 0) findTodos.updateTodoOrder(todos.getTodoOrder());
        Optional.ofNullable(todos.getTitle())
                .ifPresent(findTodos::updateTitle);
        findTodos.updateCompleted(todos.isCompleted());

        return todosRepository.save(findTodos);
    }

    @Transactional(readOnly = true)
    public Todos findVerifiedTodos(long id){
        return todosRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 Todo가 존재하지 않습니다."));
    }

    public void deleteTodos() {
        todosRepository.deleteAll();
    }

    public void deleteTodo(long id) {
        todosRepository.deleteById(id);
    }
}
