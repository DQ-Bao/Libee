package Controllers;

import Annotations.Authorize;
import Models.Cart;
import DataAccesses.CartDataAccess;
import DataAccesses.Internal.DBProps;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Authorize({"Admin", "Customer"})
public class CheckoutController extends HttpServlet {
    private CartDataAccess cartDAO;

    @Override
    public void init() throws ServletException {
        String driverName = getServletContext().getInitParameter("db-driver");
        String connectionString = getServletContext().getInitParameter("db-connection-string");
        this.cartDAO = CartDataAccess.getInstance(new DBProps(driverName, connectionString));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session == null || session.getAttribute("cart") == null) {
            resp.sendError(400);
            return;
        }
        req.getRequestDispatcher("/WEB-INF/Views/Checkout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session == null || session.getAttribute("cart") == null) {
            resp.sendError(400);
            return;
        }
        Cart cart = (Cart)session.getAttribute("cart");
        if (cart.getTotal() <= 0) {
            resp.sendRedirect(req.getContextPath());
            return;
        }
        cartDAO.checkout(cart.getId());
        session.removeAttribute("cart");
        resp.sendRedirect(req.getContextPath() + "/Cart");
    }
}
