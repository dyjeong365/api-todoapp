package com.codestates.todoapp.todos.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Todos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column(nullable = false)
    String title;
    @Column
    int todoOrder;
    @Column(columnDefinition = "boolean default false", nullable = false)
    boolean completed;

    public void updateTitle(String title){
        this.title = title;
    }

    public void updateTodoOrder(int todoOrder){
        this.todoOrder = todoOrder;
    }

    public void updateCompleted(boolean completed){
        this.completed = completed;
    }

}
