package Controllers;

import Models.Book;
import DataAccesses.BookDataAccess;
import DataAccesses.Internal.DBProps;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BookController extends HttpServlet {
    private BookDataAccess dao;

    @Override
    public void init() throws ServletException {
        String driverName = getServletContext().getInitParameter("db-driver");
        String connectionString = getServletContext().getInitParameter("db-connection-string");
        this.dao = BookDataAccess.getInstance(new DBProps(driverName, connectionString));
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String qId = req.getParameter("id");
        if (qId == null) {
            resp.sendError(404);
        }
        try {
            int id = Integer.parseInt(qId);
            Book book = dao.getById(id);
            req.setAttribute("book", book);
            req.getRequestDispatcher("/WEB-INF/Views/BookDetail.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            resp.sendError(404);
        }
    } 
}
