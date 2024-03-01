package Controllers;

import Annotations.Authorize;
import Models.Cart;
import DataAccesses.CartDataAccess;
import DataAccesses.Internal.DBProps;
import Models.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Authorize({"Admin", "Customer"})
public class CartController extends HttpServlet {
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
        User user = (User)req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/Login");
            return;
        }
        Cart activeCart = cartDAO.getActiveCartOfUser(user.getId());
        if (activeCart == null) {
            // revise later
            resp.sendError(404);
            return;
        }
        req.getSession().setAttribute("cart", activeCart);
        req.getRequestDispatcher("/WEB-INF/Views/Cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        String action = req.getParameter("form-action");
        if (action == null) {
            resp.sendError(400);
            return;
        }
        if (action.equals("add-item")) {
            Cart activeCart = (Cart)req.getSession().getAttribute("cart");
            if (activeCart == null) {
                resp.sendError(400);
                return;
            }
            int productId, quantity;
            double purchasePrice;
            try {
                productId = Integer.parseInt(req.getParameter("product-id"));
                quantity = Integer.parseInt(req.getParameter("quantity"));
                purchasePrice = Double.parseDouble(req.getParameter("purchase-price"));
                cartDAO.addOneItem(activeCart.getId(), productId, quantity, purchasePrice);
                cartDAO.calcTotal(activeCart.getId());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        if (action.equals("remove-item")) {
            Cart activeCart = (Cart)req.getSession().getAttribute("cart");
            if (activeCart == null) {
                resp.sendError(400);
                return;
            }
            try {
                int productId = Integer.parseInt(req.getParameter("product-id"));
                cartDAO.deleteOneItem(activeCart.getId(), productId);
                cartDAO.calcTotal(activeCart.getId());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        resp.sendRedirect(req.getContextPath() + "/Cart");
    }
}
