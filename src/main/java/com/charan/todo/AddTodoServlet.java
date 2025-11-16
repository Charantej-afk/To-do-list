@WebServlet("/addTodo")
public class AddTodoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String newTodo = request.getParameter("todo");
        // Add logic to save this to a list or database
        response.sendRedirect("todos");
    }
}
