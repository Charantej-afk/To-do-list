package com.charantejafk.todo;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/todos")
public class TodoServlet extends HttpServlet {

    private List<TodoItem> todos = new ArrayList<>();

    @Override
    public void init() throws ServletException {
        // Initial tasks
        todos.add(new TodoItem(1, "Buy Groceries"));
        todos.add(new TodoItem(2, "Finish Homework"));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("todos", todos);  // Pass the list to the JSP
        request.getRequestDispatcher("/WEB-INF/views/todos.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            // Add a new task
            String description = request.getParameter("description");
            if (description != null && !description.trim().isEmpty()) {
                int id = todos.size() + 1;  // New ID
                todos.add(new TodoItem(id, description));
            }
        } else if ("toggle".equals(action)) {
            // Toggle task completion
            int toggleId = Integer.parseInt(request.getParameter("toggleId"));
            for (TodoItem todo : todos) {
                if (todo.getId() == toggleId) {
                    todo.setCompleted(!todo.isCompleted());
                    break;
                }
            }
        } else if ("clearCompleted".equals(action)) {
            // Clear completed tasks
            todos.removeIf(TodoItem::isCompleted);
        }

        // Redirect to GET to refresh
        response.sendRedirect(request.getContextPath() + "/todos");
    }
}
