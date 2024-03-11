package Controllers;

import Models.Book;
import DataAccesses.BookDataAccess;
import DataAccesses.Internal.DBProps;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URLDecoder;

public class BookController extends HttpServlet {
    private BookDataAccess bookDAO;

    @Override
    public void init() throws ServletException {
        String driverName = getServletContext().getInitParameter("db-driver");
        String connectionString = getServletContext().getInitParameter("db-connection-string");
        DBProps props = new DBProps(driverName, connectionString);
        this.bookDAO = BookDataAccess.getInstance(props);
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String url = req.getRequestURI();
        String endpoint = url.substring(url.lastIndexOf("/") + 1, !url.contains("?") ? url.length() : url.indexOf("?"));
        if (!endpoint.equals("Book")) {
            req.setAttribute("productList", bookDAO.getAllOfGenre(URLDecoder.decode(endpoint, "UTF-8")));
            req.getRequestDispatcher("/WEB-INF/Views/ProductDisplay.jsp").forward(req, resp);
            return;
        }
        String qId = req.getParameter("id");
        if (qId == null) {
            resp.sendError(404);
            return;
        }
        try {
            int id = Integer.parseInt(qId);
            Book book = bookDAO.getById(id);
            req.setAttribute("book", book);
            req.getRequestDispatcher("/WEB-INF/Views/BookDetail.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            resp.sendError(404);
        }
    } 
}
