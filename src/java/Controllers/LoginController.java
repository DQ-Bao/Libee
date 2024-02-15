package Controllers;

import Models.User;
import DataAccesses.UserDataAccess;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginController extends HttpServlet {
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
            req.getRequestDispatcher("Views/Login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        User user = dao.login(email, password);
        if (user == null) {
            req.setAttribute("message", "Login Failed!");
            req.getRequestDispatcher("Views/Login.jsp").forward(req, resp);
        }
        else {
            req.getSession().setAttribute("user", user);
            resp.sendRedirect(req.getContextPath());
        }
    }
}
