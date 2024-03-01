package Controllers;

import DataAccesses.Internal.DBProps;
import Models.User;
import DataAccesses.UserDataAccess;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RegisterController extends HttpServlet {
    private UserDataAccess dao;

    @Override
    public void init() throws ServletException {
        String driverName = getServletContext().getInitParameter("db-driver");
        String connectionString = getServletContext().getInitParameter("db-connection-string");
        this.dao = UserDataAccess.getInstance(new DBProps(driverName, connectionString));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        User user = (User)req.getSession().getAttribute("user");
        if (user != null) {
            resp.sendRedirect(req.getContextPath());
        }
        else {
            req.getRequestDispatcher("/WEB-INF/Views/Register.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        String firstName = req.getParameter("first-name");
        String lastName = req.getParameter("last-name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        boolean success = dao.register(firstName, lastName, email, password);
        if (!success) {
            req.setAttribute("message", "Register Failed!");
            req.getRequestDispatcher("/WEB-INF/Views/Register.jsp").forward(req, resp);
        }
        else {
            resp.sendRedirect(req.getContextPath() + "/Login");
        }
    }
}
