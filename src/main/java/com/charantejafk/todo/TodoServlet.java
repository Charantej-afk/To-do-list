package com.charantejafk.todo;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/todos")
public class TodoServlet extends HttpServlet {
    
    private transient List<TodoItem> todos = new ArrayList<>();

    @Override
    public void init() throws ServletException {
        // Initial tasks
        todos.add(new TodoItem(1, "Buy Groceries"));
        todos.add(new TodoItem(2, "Finish Homework"));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("todos", todos);
        request.getRequestDispatcher("/WEB-INF/views/todos.jsp").forward(request, response);
    }
    
    // Make todos accessible to other servlets
    public List<TodoItem> getTodos() {
        return todos;
    }
}
