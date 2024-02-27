package Models;

import Models.Publisher.PublisherBuilder;
import Models.AuthorOfBook.AuthorOfBookBuilder;
import Models.SubCategoryOfBook.SubCategoryOfBookBuilder;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    
    public Book(BookBuilder builder) {
        super(builder);
        this.isbn10 = builder.isbn10;
        this.isbn13 = builder.isbn13;
        this.language = builder.language;
        this.publisher = builder.publisher;
        this.publicationDate = builder.publicationDate;
        this.authors = builder.authors;
        this.genres = builder.genres;
    }
    
    public static BookBuilder getBuilder() {
        return new BookBuilder();
    }
    
    public static class BookBuilder extends ProductBuilder<BookBuilder> {
        private String isbn10;
        private String isbn13;
        private String language;
        private Publisher publisher;
        private LocalDateTime publicationDate;
        private List<AuthorOfBook> authors;
        private List<SubCategoryOfBook> genres;
        
        private BookBuilder() {
            super();
            this.authors = new ArrayList<>();
            this.genres = new ArrayList<>();
        }
        
        public BookBuilder ISBN10(String isbn10) { this.isbn10 = isbn10; return this; }
        public BookBuilder ISBN13(String isbn13) { this.isbn13 = isbn13; return this; }
        public BookBuilder Language(String language) { this.language = language; return this; }
        public BookBuilder Publisher(PublisherBuilder builder) { this.publisher = builder.Build(); return this; }
        public BookBuilder PublicationDate(LocalDateTime publicationDate) { this.publicationDate = publicationDate; return this; }
        public BookBuilder Author(AuthorOfBookBuilder builder) { this.authors.add(builder.Build()); return this; }
        public BookBuilder Genre(SubCategoryOfBookBuilder builder) { this.genres.add(builder.Build()); return this; }
        @Override
        public Book Build() {
            return new Book(this);
        }
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
