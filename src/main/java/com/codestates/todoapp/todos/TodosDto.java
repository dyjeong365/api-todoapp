package com.codestates.todoapp.todos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class TodosDto {
    private TodosDto() {
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Request {
        String title;
        int todoOrder;
        boolean completed;
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Response {
        long id;
        String title;
        int todoOrder;
        boolean completed;
    }
}
