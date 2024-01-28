package Models;

import java.util.Date;

public class Book {
    private int id;
    private String isbn10;
    private String isbn13;
    private String title;
    private String language;
    private String coverPath;
    private String buyLink;
    private String description;
    private int publisherId;
    private Date publicationDate;

    public Book() {
    }

    public Book(int id, String isbn10, String isbn13, String title, String language, String coverPath, String buyLink, String description, int publisherId, Date publicationDate) {
        this.id = id;
        this.isbn10 = isbn10;
        this.isbn13 = isbn13;
        this.title = title;
        this.language = language;
        this.coverPath = coverPath;
        this.buyLink = buyLink;
        this.description = description;
        this.publisherId = publisherId;
        this.publicationDate = publicationDate;
    }

    public int getId() {
        return id;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public String getTitle() {
        return title;
    }

    public String getLanguage() {
        return language;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public String getBuyLink() {
        return buyLink;
    }

    public String getDescription() {
        return description;
    }

    public int getPublisherId() {
        return publisherId;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public void setBuyLink(String buyLink) {
        this.buyLink = buyLink;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }
}
