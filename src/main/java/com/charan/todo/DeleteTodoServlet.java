@WebServlet("/deleteTodo")
public class DeleteTodoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String todoToDelete = request.getParameter("todo");
        // Add logic to delete from list or database
        response.sendRedirect("todos");
    }
}
