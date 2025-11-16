package com.charantejafk.todo;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@WebServlet("/todos")
public class TodoServlet extends HttpServlet {
    
    private transient List<TodoItem> todos = new ArrayList<>();
    private transient AtomicInteger idCounter = new AtomicInteger(3); // Start from 3 since we have 2 initial items

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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            String description = request.getParameter("description");
            if (description != null && !description.trim().isEmpty()) {
                int id = idCounter.getAndIncrement();
                todos.add(new TodoItem(id, description.trim()));
            }
        } else if ("toggle".equals(action)) {
            try {
                int toggleId = Integer.parseInt(request.getParameter("toggleId"));
                for (TodoItem todo : todos) {
                    if (todo.getId() == toggleId) {
                        todo.setCompleted(!todo.isCompleted());
                        break;
                    }
                }
            } catch (NumberFormatException e) {
                // Log error
                System.err.println("Invalid toggleId: " + request.getParameter("toggleId"));
            }
        } else if ("delete".equals(action)) {
            try {
                int deleteId = Integer.parseInt(request.getParameter("deleteId"));
                todos.removeIf(todo -> todo.getId() == deleteId);
            } catch (NumberFormatException e) {
                // Log error
                System.err.println("Invalid deleteId: " + request.getParameter("deleteId"));
            }
        } else if ("clearCompleted".equals(action)) {
            todos.removeIf(TodoItem::isCompleted);
        }

        response.sendRedirect(request.getContextPath() + "/todos");
    }
}
