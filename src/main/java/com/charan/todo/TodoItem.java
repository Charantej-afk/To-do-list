package com.charan.todo;

import java.time.Instant;

public class TodoItem {
    private final int id;
    private final String description;
    private boolean completed;
    private final Instant createdAt;

    public TodoItem(int id, String description) {
        this.id = id;
        this.description = description;
        this.createdAt = Instant.now();
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void toggle() {
        this.completed = !this.completed;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
