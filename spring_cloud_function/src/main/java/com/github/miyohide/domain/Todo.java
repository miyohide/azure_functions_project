package com.github.miyohide.domain;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

public class Todo {
    @Id
    private Integer id;
    private String title;
    private boolean finished;
    private LocalDateTime createdAt;

    public Todo(String title) {
        this.title = title;
        this.finished = false;
        this.createdAt = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}