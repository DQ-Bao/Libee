package Controllers;

import Models.User;
import Models.Cart;
import DataAccesses.UserDataAccess;
import DataAccesses.CartDataAccess;
import DataAccesses.Internal.DBProps;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginController extends HttpServlet {
    private UserDataAccess userDAO;
    private CartDataAccess cartDAO;

    @Override
    public void init() throws ServletException {
        String driverName = getServletContext().getInitParameter("db-driver");
        String connectionString = getServletContext().getInitParameter("db-connection-string");
        DBProps props = new DBProps(driverName, connectionString);
        this.userDAO = UserDataAccess.getInstance(props);
        this.cartDAO = CartDataAccess.getInstance(props);
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        User user = (User)req.getSession().getAttribute("user");
        if (user != null) {
            resp.sendRedirect(req.getContextPath());
        }
        else {
            req.getRequestDispatcher("/WEB-INF/Views/Login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        User user = userDAO.login(email, password);
        if (user == null) {
            req.setAttribute("message", "Login Failed!");
            req.getRequestDispatcher("/WEB-INF/Views/Login.jsp").forward(req, resp);
        }
        else {
            Cart cart = cartDAO.getActiveCartOfUser(user.getId());
            if (cart != null) {
                req.getSession().setAttribute("cart", cart);
            }
            req.getSession().setAttribute("user", user);
            for (int i = 0; i < user.getRoles().size(); i++) {
                if (user.getRoles().get(i).getName().equals("Admin")) {
                    req.getSession().setAttribute("isAdmin", true);
                }
            }
            resp.sendRedirect(req.getContextPath());
        }
    }
}
