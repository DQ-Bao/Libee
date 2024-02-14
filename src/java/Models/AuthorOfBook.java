package Models;

public class AuthorOfBook {
    private int bookId;
    private int authorId;
    private String authorName;
    private String authorImagePath;
    private String authorDescription;

    public AuthorOfBook() {
    }

    public AuthorOfBook(int bookId, int authorId, String authorName, String authorImagePath, String authorDescription) {
        this.bookId = bookId;
        this.authorId = authorId;
        this.authorName = authorName;
        this.authorImagePath = authorImagePath;
        this.authorDescription = authorDescription;
    }

    public int getBookId() {
        return bookId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getAuthorImagePath() {
        return authorImagePath;
    }

    public String getAuthorDescription() {
        return authorDescription;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setAuthorImagePath(String authorImagePath) {
        this.authorImagePath = authorImagePath;
    }

    public void setAuthorDescription(String authorDescription) {
        this.authorDescription = authorDescription;
    }
}
