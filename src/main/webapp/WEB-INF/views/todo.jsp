<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.charantejafk.todo.TodoItem" %>
<!DOCTYPE html>
<html>
<head>
    <title>Todo List</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        .completed { text-decoration: line-through; color: #888; }
        form { margin: 10px 0; }
        input[type="text"] { padding: 8px; width: 300px; }
        button { padding: 8px 16px; margin: 5px; }
        ul { list-style-type: none; padding: 0; }
        li { padding: 5px 0; }
        .todo-item { display: flex; align-items: center; gap: 10px; }
    </style>
</head>
<body>
    <h1>My Todo List</h1>

    <!-- Add new task form -->
    <form method="post" action="addTodo">
        <input type="text" name="description" placeholder="Add a new task..." required>
        <button type="submit">Add Task</button>
    </form>

    <!-- Todo list -->
    <ul>
        <%
            List<TodoItem> todos = (List<TodoItem>) request.getAttribute("todos");
            if (todos != null) {
                for (TodoItem todo : todos) {
        %>
            <li class="todo-item">
                <form method="post" action="updateTodo" style="display:inline;">
                    <input type="hidden" name="id" value="<%= todo.getId() %>">
                    <input type="hidden" name="completed" value="<%= !todo.isCompleted() %>">
                    <input type="checkbox" <%= todo.isCompleted() ? "checked" : "" %> onchange="this.form.submit()">
                </form>
                <span class="<%= todo.isCompleted() ? "completed" : "" %>">
                    <%= todo.getDescription() %>
                </span>
                
                <!-- Delete button -->
                <form method="post" action="deleteTodo" style="display:inline;">
                    <input type="hidden" name="id" value="<%= todo.getId() %>">
                    <button type="submit">Delete</button>
                </form>
            </li>
        <%
                }
            }
        %>
    </ul>

    <!-- Clear completed tasks -->
    <form method="post" action="clearTodos">
        <button type="submit">Clear Completed Tasks</button>
    </form>

    <p><strong>Total tasks:</strong> <%= todos != null ? todos.size() : 0 %></p>
</body>
</html>
