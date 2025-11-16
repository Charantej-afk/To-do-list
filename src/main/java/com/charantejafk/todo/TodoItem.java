package com.charantejafk.todo;

public class TodoItem {
    private int id;
    private String description;
    private boolean completed;
    
    public TodoItem(int id, String description) {
        this.id = id;
        this.description = description;
        this.completed = false;
    }
    
    // Getters
    public int getId() { return id; }
    public String getDescription() { return description; }
    public boolean isCompleted() { return completed; }
    
    // Setters
    public void setId(int id) { this.id = id; }
    public void setDescription(String description) { this.description = description; }
    public void setCompleted(boolean completed) { this.completed = completed; }
}
