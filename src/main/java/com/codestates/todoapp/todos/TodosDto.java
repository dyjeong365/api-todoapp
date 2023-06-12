package com.codestates.todoapp.todos;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TodosDto {

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
