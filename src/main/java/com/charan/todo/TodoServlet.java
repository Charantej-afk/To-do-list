package com.charan.todo;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

@WebServlet("/todos") // Mapped to /todos URL
public class TodoServlet extends HttpServlet {

    private List<TodoItem> todos = new ArrayList<>();

    @Override
    public void init() throws ServletException {
        // Initialize with some sample todos
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
        String newTodo = request.getParameter("todo");
        if (newTodo != null && !newTodo.trim().isEmpty()) {
            int id = todos.size() + 1;
            todos.add(new TodoItem(id, newTodo));
        }
        response.sendRedirect("todos");
    }
}
