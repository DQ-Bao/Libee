package Controllers.Admin;

import Annotations.Authorize;
import DataAccesses.Internal.DBProps;
import DataAccesses.ProductDataAccess;
import Utils.ImageUtils;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Authorize({"Admin"})
public class DeleteProductController extends HttpServlet {
    private static String IMAGE_LOCATION;
    private ProductDataAccess productDAO;

    @Override
    public void init() throws ServletException {
        String driverName = getServletContext().getInitParameter("db-driver");
        String connectionString = getServletContext().getInitParameter("db-connection-string");
        this.productDAO = ProductDataAccess.getInstance(new DBProps(driverName, connectionString));
        IMAGE_LOCATION = getServletContext().getInitParameter("image-location");
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        String idParam = req.getParameter("product-id");
        String imagePath = req.getParameter("product-imagePath");
        if (idParam != null) {
            try {
                int productId = Integer.parseInt(idParam);
                productDAO.deleteOne(productId);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        if (imagePath != null && !imagePath.isBlank()) {
            ImageUtils.deleteImage(IMAGE_LOCATION + "/" + imagePath);
        }
        String referer = req.getHeader("referer");
        resp.sendRedirect(referer == null ? req.getContextPath() + "/Admin/Product" : referer);
    }
}
