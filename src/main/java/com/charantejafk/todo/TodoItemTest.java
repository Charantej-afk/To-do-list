package com.charantejafk.todo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TodoItemTest {
    
    @Test
    public void testTodoItemCreation() {
        TodoItem item = new TodoItem(1, "Test Task");
        assertEquals(1, item.getId());
        assertEquals("Test Task", item.getDescription());
        assertFalse(item.isCompleted());
    }
    
    @Test
    public void testTodoItemSetters() {
        TodoItem item = new TodoItem(1, "Test Task");
        item.setCompleted(true);
        item.setDescription("Updated Task");
        
        assertTrue(item.isCompleted());
        assertEquals("Updated Task", item.getDescription());
    }
}
