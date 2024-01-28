package Services;

import Models.Book;
import Models.Author;
import Models.Publisher;
import Models.BookDTO;
import DataAccesses.BookDataAccess;
import java.util.List;
import java.util.ArrayList;

public class BookService {
    private BookDataAccess dao;

    public BookService() {
        this.dao = new BookDataAccess();
    }
    
    public List<BookDTO> getAll() {
        List<BookDTO> dtos = new ArrayList<>();
        List<Book> books = dao.getAll();
        for (int i = 0; i < books.size(); i++) {
            BookDTO dto = new BookDTO();
            dto.setBook(books.get(i));
            dto.setAuthors(dao.getAuthors(books.get(i).getId()));
            dto.setPublisher(dao.getPublisher(books.get(i).getId()));
            dtos.add(dto);
        }
        return dtos;
    }
}
