package Models;

import java.util.List;

public class BookDTO {
    private Book book;
    private List<Author> authors;
    private Publisher publisher;

    public BookDTO() {
    }

    public Book getBook() {
        return book;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }
}
