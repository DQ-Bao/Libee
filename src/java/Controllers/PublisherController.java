package Controllers;

import Annotations.Authorize;
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
    @Authorize({"Admin"})
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        String action = req.getParameter("form-action");
        if (action == null) {
            resp.sendError(400);
            return;
        }
        if (action.equals("add-publisher")) {
            String name = req.getParameter("add-publisher-name");
            publisherDAO.addOne(Publisher.getBuilder().Name(name).Build());
        }
        else if (action.equals("change-publisher")) {
            int id = Integer.parseInt(req.getParameter("change-publisher-id"));
            String act = req.getParameter("action");
            if (act.equals("update")) {
                String name = req.getParameter("update-publisher-name");
                publisherDAO.updateOne(new Publisher(id, name));
            }
            else if (act.equals("delete")) {
                publisherDAO.deleteOne(id);
            }
        }
        resp.sendRedirect(req.getContextPath() + "/Admin/Product");
    }
}
