package com.codestates.todoapp.todos;

import com.codestates.todoapp.todos.domain.Todos;
import com.codestates.todoapp.todos.domain.TodosRepository;
import com.codestates.todoapp.todos.service.TodosService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import java.util.Optional;

@SpringBootTest
public class TodosServiceTest {
    @Autowired
    private TodosService service;

    @Autowired
    private TodosRepository repository;

    @AfterEach
    public void clean(){
        service.deleteTodos();
    }

    @Test
    @DisplayName("서비스에서 Todo가 수정된다.")
    void updateTodo() throws Exception {
        //given
        Todos todos = Todos.builder()
                .todoOrder(1)
                .id(1L)
                .completed(false)
                .title("잠자기")
                .build();
        Todos updateTodos = Todos.builder()
                .title("안녕")
                .build();
        //when
        repository.save(todos);
        service.updateTodo(todos.getId(), updateTodos);

        Todos findTodos = repository.findById(todos.getId()).orElseThrow();
        //then
        assertThat(findTodos.getId()).isEqualTo(todos.getId()); // 변하지 않는
        assertThat(findTodos.getTodoOrder()).isEqualTo(todos.getTodoOrder()); // 변하지 않는
        assertThat(findTodos.getTitle()).isEqualTo(updateTodos.getTitle()); // 수정된

    }
}
