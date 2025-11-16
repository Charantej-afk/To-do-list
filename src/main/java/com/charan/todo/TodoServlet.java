package com.charantejafk.todo;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet("/todos")  // Mapped to /todos URL
public class TodoServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Static list of todos (simple example)
        List<String> todos = Arrays.asList("Buy Groceries", "Finish Homework", "Clean House");
        
        // Set todos as request attribute to pass to JSP
        request.setAttribute("todos", todos);
        
        // Forward the request to the todos.jsp page
        request.getRequestDispatcher("/WEB-INF/views/todos.jsp").forward(request, response);
    }
}
