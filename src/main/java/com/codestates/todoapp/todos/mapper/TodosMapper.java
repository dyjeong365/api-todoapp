package com.codestates.todoapp.todos.mapper;

import com.codestates.todoapp.todos.TodosDto;
import com.codestates.todoapp.todos.domain.Todos;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TodosMapper {
    Todos todosRequestDtoToTodos(TodosDto.Request request);
    TodosDto.Response todosToTodosResponseDto(Todos todos);
    List<TodosDto.Response> todoListToTodoResponseDtoList(List<Todos> todos);
}
