package com.charantejafk.todo;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/todos")
public class TodoServlet extends HttpServlet {
    
    // Make the list transient or ensure TodoItem is serializable
    private transient List<TodoItem> todos = new ArrayList<>();

    @Override
    public void init() throws ServletException {
        // Initial tasks
        todos.add(new TodoItem(1, "Buy Groceries"));
        todos.add(new TodoItem(2, "Finish Homework"));
    }

    // ... rest of your code
}
