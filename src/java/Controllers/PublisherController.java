package Controllers;

import Annotations.Authorize;
import Models.Publisher;
import Services.PublisherService;
import java.util.List;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Authorize({"Admin", "Customer"})
public class PublisherController extends HttpServlet {  
    private PublisherService service;

    @Override
    public void init() throws ServletException {
        this.service = new PublisherService();
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action != null) {
            if (action.equals("update")) {
                int id = Integer.parseInt(req.getParameter("id"));
                Publisher pub = service.getById(id);
                req.setAttribute("update_pub", pub);
            }
            else if (action.equals("delete")) {
                int id = Integer.parseInt(req.getParameter("id"));
                service.deleteOne(id);
            }
            req.removeAttribute("action");
        }
        List<Publisher> publishers = service.getAll();
        req.setAttribute("publishers", publishers);
        req.getRequestDispatcher("WEB-INF/Views/Publisher.jsp").forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action != null) {
            if (action.equals("add")) {
                String name = req.getParameter("add_pub_name");
                service.addOne(name);
            }
            else if (action.equals("update")) {
                int id = Integer.parseInt(req.getParameter("update_pub_id"));
                String name = req.getParameter("update_new_name");
                service.updateOne(new Publisher(id, name));
            }
            req.removeAttribute("action");
        }
        res.sendRedirect(req.getContextPath() + "/Publisher");
    }
}
