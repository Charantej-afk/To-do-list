package com.charan.todo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@WebServlet(name = "TodoServlet", urlPatterns = "/todos")
public class TodoServlet extends HttpServlet {

    private final AtomicInteger idCounter = new AtomicInteger(1);
    private final CopyOnWriteArrayList<TodoItem> todos = new CopyOnWriteArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("todos", List.copyOf(todos));
        req.getRequestDispatcher("/WEB-INF/views/todo.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("add".equals(action)) {
            handleAdd(req);
        } else if ("toggle".equals(action)) {
            handleToggle(req);
        } else if ("clearCompleted".equals(action)) {
            todos.removeIf(TodoItem::isCompleted);
        }

        resp.sendRedirect(req.getContextPath() + "/todos");
    }

    private void handleAdd(HttpServletRequest req) {
        String description = req.getParameter("description");
        if (description == null) {
            return;
        }

        String trimmed = description.trim();
        if (!trimmed.isEmpty()) {
            todos.add(new TodoItem(idCounter.getAndIncrement(), trimmed));
        }
    }

    private void handleToggle(HttpServletRequest req) {
        String idParam = req.getParameter("toggleId");
        if (idParam == null) {
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            todos.stream()
                    .filter(todo -> todo.getId() == id)
                    .findFirst()
                    .ifPresent(TodoItem::toggle);
        } catch (NumberFormatException ignored) {
            // ignore malformed toggle id
        }
    }
}
