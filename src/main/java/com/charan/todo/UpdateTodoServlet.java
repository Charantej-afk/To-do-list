@WebServlet("/updateTodo")
public class UpdateTodoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String oldTodo = request.getParameter("oldTodo");
        String newTodo = request.getParameter("newTodo");
        // Add logic to update the todo in your list or database
        response.sendRedirect("todos");
    }
}
