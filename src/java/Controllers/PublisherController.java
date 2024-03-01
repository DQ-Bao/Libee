package Controllers;

import Models.Publisher;
import DataAccesses.Internal.DBProps;
import DataAccesses.PublisherDataAccess;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PublisherController extends HttpServlet {
    private PublisherDataAccess publisherDAO;

    @Override
    public void init() throws ServletException {
        String driverName = getServletContext().getInitParameter("db-driver");
        String connectionString = getServletContext().getInitParameter("db-connection-string");
        this.publisherDAO = PublisherDataAccess.getInstance(new DBProps(driverName, connectionString));
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
            publisherDAO.addOne(Publisher.getBuilder().Name(name).Build());
        }
        resp.sendRedirect(req.getContextPath() + "/Admin");
    }
}
