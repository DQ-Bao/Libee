package Controllers;

import Annotations.Authorize;
import Models.User;
import DataAccesses.Internal.DBProps;
import DataAccesses.UserDataAccess;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Authorize({"Admin", "Customer"})
public class UserController extends HttpServlet {
    private UserDataAccess userDAO;

    @Override
    public void init() throws ServletException {
        String driverName = getServletContext().getInitParameter("db-driver");
        String connectionString = getServletContext().getInitParameter("db-connection-string");
        this.userDAO = UserDataAccess.getInstance(new DBProps(driverName, connectionString));
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        String error = req.getParameter("error");
        if (error != null) {
            req.setAttribute("error", error);
        }
        req.getRequestDispatcher("/WEB-INF/Views/User.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        String action = req.getParameter("form-action");
        if (action == null) {
            resp.sendError(400);
            return;
        }
        User user = (User)req.getSession().getAttribute("user");
        if (action.equals("user-change-password")) {
            String oldPassword = req.getParameter("old-password");
            String newPassword = req.getParameter("new-password");
            if (!userDAO.checkPassword(user.getId(), oldPassword)) {
                req.setAttribute("error", "Wrong password");
                req.getRequestDispatcher("/WEB-INF/Views/User.jsp").forward(req, resp);
                return;
            }
            userDAO.changePassword(user.getId(), newPassword);
            resp.sendRedirect(req.getContextPath() + "/Logout");
            return;
        }
        if (action.equals("user-delete-account")) {
            userDAO.deleteAccount(user.getId());
            resp.sendRedirect(req.getContextPath() + "/Logout");
            return;
        }
        resp.sendRedirect(req.getContextPath() + "/User");
    }
}
