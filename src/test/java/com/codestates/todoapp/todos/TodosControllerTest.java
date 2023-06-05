package com.codestates.todoapp.todos;

import com.codestates.todoapp.todos.domain.Todos;
import com.codestates.todoapp.todos.mapper.TodosMapper;
import com.codestates.todoapp.todos.service.TodosService;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class TodosControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private TodosService service;

    @MockBean
    private TodosMapper mapper;

    @Test
    @DisplayName("Todos를 추가한다(Post)")
    public void postTodos() throws Exception {
        //given
        TodosDto.Request request = TodosDto.Request.builder()
                .todoOrder(1)
                .completed(false)
                .title("잠자기")
                .build();

        String content = gson.toJson(request);

        given(mapper.todosRequestDtoToTodos(Mockito.any(TodosDto.Request.class))).willReturn(new Todos());

        Todos mockTodos = Todos.builder()
                .id(1L)
                .completed(false)
                .title("잠자기")
                .todoOrder(1)
                .build();

        TodosDto.Response response= TodosDto.Response.builder()
                .todoOrder(1)
                .completed(false)
                .title("잠자기")
                .id(1L)
                .build();

        given(service.createTodo(Mockito.any(Todos.class))).willReturn(mockTodos);
        given(mapper.todosToTodosResponseDto(Mockito.any(Todos.class))).willReturn(response);
        //when
        ResultActions actions =
                mockMvc.perform(
                                post("/")
                                        .accept(MediaType.APPLICATION_JSON)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(content)
                        )
                        //then
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.title").value("잠자기"));
    }

    @Test
    @DisplayName("Todos리스트를 읽어온다")
    void getTodos() throws Exception {
        //given
        List<Todos> todosList = List.of(
                Todos.builder()
                        .title("잠자기")
                        .completed(false)
                        .todoOrder(1)
                        .build(),
                Todos.builder()
                        .title("책보기")
                        .completed(false)
                        .todoOrder(2)
                        .build()
        );

        List<TodosDto.Response> responseList = List.of(
                TodosDto.Response.builder()
                        .title("잠자기")
                        .completed(false)
                        .todoOrder(1)
                        .build(),
                TodosDto.Response.builder()
                        .title("책보기")
                        .completed(false)
                        .todoOrder(2)
                        .build()
        );


        given(service.readTodos()).willReturn(todosList);
        given(mapper.todoListToTodoResponseDtoList(Mockito.anyList())).willReturn(responseList);

        ResultActions actions =
                mockMvc.perform(
                                get("/")
                                        .accept(MediaType.APPLICATION_JSON)
                        )
                        //then
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.[0].title").value("잠자기"))
                        .andExpect(jsonPath("$.[1].title").value("책보기"));

    }

    @Test
    @DisplayName("Todo 아이디로 한개만 읽어온다")
    void getTodo() throws Exception {
        //given
        Todos todos = Todos.builder()
                .completed(false)
                .title("잠자기")
                .id(1L)
                .todoOrder(1)
                .build();

        TodosDto.Response response= TodosDto.Response.builder()
                .todoOrder(1)
                .completed(false)
                .title("잠자기")
                .id(1L)
                .build();

        given(service.readTodo(Mockito.anyLong())).willReturn(todos);
        given(mapper.todosToTodosResponseDto(Mockito.any(Todos.class))).willReturn(response);
        //when
        ResultActions actions =
                mockMvc.perform(
                                get("/1")
                                        .accept(MediaType.APPLICATION_JSON)
                        )
                        //then
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.title").value("잠자기"));

    }

    @Test
    @DisplayName("Todos가 수정된다.")
    void patchTodo() throws Exception {
        //given
        TodosDto.Request request = TodosDto.Request.builder()
                .todoOrder(1)
                .completed(false)
                .title("잠자기")
                .build();

        String content = gson.toJson(request);

        Todos todos = Todos.builder()
                .todoOrder(1)
                .completed(true)
                .title("책보기")
                .build();

        TodosDto.Response response = TodosDto.Response.builder()
                .todoOrder(1)
                .completed(true)
                .title("책보기")
                .build();

        given(mapper.todosRequestDtoToTodos(Mockito.any(TodosDto.Request.class))).willReturn(new Todos());
        given(service.updateTodo(Mockito.anyLong(), Mockito.any(Todos.class))).willReturn(todos);
        given(mapper.todosToTodosResponseDto(Mockito.any(Todos.class))).willReturn(response);
        //when
        ResultActions actions =
                mockMvc.perform(
                                patch("/1")
                                        .accept(MediaType.APPLICATION_JSON)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(content)
                        )
                        //then
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.title").value("책보기"))
                        .andExpect(jsonPath("$.completed").value(true));

    }

    @Test
    @DisplayName("Todo가 한 개 삭제된다.")
    void deleteTodo() throws Exception {
        //given
        doNothing().when(service).deleteTodo(Mockito.anyLong());
        //when
        mockMvc.perform(delete("/1"))
                .andExpect(status().isNoContent());
    }

}
