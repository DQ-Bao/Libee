package Controllers;

import Models.Publisher;
import Services.ProductService;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PublisherController extends HttpServlet {
    private ProductService productSVC;

    @Override
    public void init() throws ServletException {
        this.productSVC = ProductService.getInstance();
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            resp.sendError(400);
            return;
        }
        if (action.equals("add-publisher")) {
            String name = req.getParameter("publisher-name");
            productSVC.addOnePublisher(Publisher.getBuilder().Name(name).Build());
        }
        resp.sendRedirect(req.getContextPath() + "/Admin");
    }
}
