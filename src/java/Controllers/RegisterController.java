package Controllers;

import Models.User;
import DataAccesses.UserDataAccess;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RegisterController extends HttpServlet {
    private UserDataAccess dao;

    @Override
    public void init() throws ServletException {
        this.dao = UserDataAccess.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        User user = (User)req.getSession().getAttribute("user");
        if (user != null) {
            resp.sendRedirect(req.getContextPath());
        }
        else {
            req.getRequestDispatcher("WEB-INF/Views/Register.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        String firstName = req.getParameter("first_name");
        String lastName = req.getParameter("last_name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        boolean success = dao.register(firstName, lastName, email, password);
        if (!success) {
            req.setAttribute("message", "Register Failed!");
            req.getRequestDispatcher("WEB-INF/Views/Register.jsp").forward(req, resp);
        }
        else {
            resp.sendRedirect(req.getContextPath() + "/Login");
        }
    }
}
