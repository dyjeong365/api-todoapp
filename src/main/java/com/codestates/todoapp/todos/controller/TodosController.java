package com.codestates.todoapp.todos.controller;

import com.codestates.todoapp.todos.TodosDto;
import com.codestates.todoapp.todos.domain.Todos;
import com.codestates.todoapp.todos.mapper.TodosMapper;
import com.codestates.todoapp.todos.service.TodosService;
import com.codestates.utils.UriCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/api/todos")
public class TodosController {

    private final TodosService todosService;
    private final TodosMapper todosMapper;

    @GetMapping
    public ResponseEntity readTodos(){
        List<Todos> todosList = todosService.readTodos();
        List<TodosDto.Response> responseList = todosMapper.todoListToTodoResponseDtoList(todosList);
        return new ResponseEntity(responseList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity readTodo(@PathVariable("id") long id){
        Todos todos = todosService.readTodo(id);
        TodosDto.Response response = todosMapper.todosToTodosResponseDto(todos);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createTodo(@RequestBody TodosDto.Request request){
        Todos todos = todosMapper.todosRequestDtoToTodos(request);
        TodosDto.Response createTodos = todosMapper.todosToTodosResponseDto(todosService.createTodo(todos));

        return new ResponseEntity(createTodos, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity updateTodo(@PathVariable("id") long id, @RequestBody TodosDto.Request request){
        Todos todos = todosMapper.todosRequestDtoToTodos(request);
        Todos updateTodos = todosService.updateTodo(id, todos);
        return new ResponseEntity(todosMapper.todosToTodosResponseDto(updateTodos), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity deleteTodos(){
        todosService.deleteTodos();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTodo(@PathVariable("id") long id){
        todosService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }

}
