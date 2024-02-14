package Controllers;

import Models.Book;
import DataAccesses.BookDataAccess;
import java.util.List;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BookController extends HttpServlet {
    private BookDataAccess dao;

    @Override
    public void init() throws ServletException {
        dao = BookDataAccess.getInstance();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        List<Book> list = dao.getAll();
    } 
}
