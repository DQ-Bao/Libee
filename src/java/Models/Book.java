package Models;

import java.time.LocalDateTime;
import java.util.List;

public class Book extends Product {
    private String isbn10;
    private String isbn13;
    private String language;
    private Publisher publisher;
    private LocalDateTime publicationDate;
    private List<AuthorOfBook> authors;
    private List<SubCategoryOfBook> genres;

    public Book() {
    }

    public Book(int id, String name, double price, String description, int categoryId, Category category, int quantityInStock, String imagePath, String isbn10, String isbn13, String language, Publisher publisher, LocalDateTime publicationDate, List<AuthorOfBook> authors, List<SubCategoryOfBook> genres) {
        super(id, name, price, description, categoryId, category, quantityInStock, imagePath);
        this.isbn10 = isbn10;
        this.isbn13 = isbn13;
        this.language = language;
        this.publisher = publisher;
        this.publicationDate = publicationDate;
        this.authors = authors;
        this.genres = genres;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public LocalDateTime getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDateTime publicationDate) {
        this.publicationDate = publicationDate;
    }

    public List<AuthorOfBook> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorOfBook> authors) {
        this.authors = authors;
    }

    public List<SubCategoryOfBook> getGenres() {
        return genres;
    }

    public void setGenres(List<SubCategoryOfBook> genres) {
        this.genres = genres;
    }
}
