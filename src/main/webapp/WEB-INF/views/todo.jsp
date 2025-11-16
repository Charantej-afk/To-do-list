<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.charan.todo.TodoItem" %>
<%
    List<TodoItem> todos = (List<TodoItem>) request.getAttribute("todos");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Todo List</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 2rem auto; max-width: 600px; }
        h1 { text-align: center; }
        form { margin-bottom: 1rem; }
        ul { list-style: none; padding: 0; }
        li { display: flex; justify-content: space-between; align-items: center; padding: 0.5rem; border-bottom: 1px solid #e2e2e2; }
        .completed { text-decoration: line-through; color: #777; }
        .actions { display: flex; gap: 0.5rem; }
        button { cursor: pointer; }
    </style>
</head>
<body>
<h1>Todo List</h1>

<form method="post" action="<%=request.getContextPath()%>/todos">
    <input type="hidden" name="action" value="add"/>
    <input type="text" name="description" placeholder="Add a new task" required style="width:70%"/>
    <button type="submit">Add</button>
</form>

<% if (todos == null || todos.isEmpty()) { %>
    <p>No tasks yet. Add your first todo!</p>
<% } else { %>
    <ul>
        <% for (TodoItem todo : todos) { %>
            <li>
                <span class="<%=todo.isCompleted() ? "completed" : ""%>">
                    #<%=todo.getId()%> - <%=todo.getDescription()%>
                </span>
                <div class="actions">
                    <form method="post" action="<%=request.getContextPath()%>/todos">
                        <input type="hidden" name="action" value="toggle"/>
                        <input type="hidden" name="toggleId" value="<%=todo.getId()%>"/>
                        <button type="submit"><%=todo.isCompleted() ? "Undo" : "Done"%></button>
                    </form>
                </div>
            </li>
        <% } %>
    </ul>
    <form method="post" action="<%=request.getContextPath()%>/todos">
        <input type="hidden" name="action" value="clearCompleted"/>
        <button type="submit">Clear Completed</button>
    </form>
<% } %>

</body>
</html>
