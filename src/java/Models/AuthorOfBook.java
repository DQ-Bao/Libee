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
    
    public AuthorOfBook(AuthorOfBookBuilder builder) {
        this.bookId = builder.bookId;
        this.authorId = builder.authorId;
        this.authorName = builder.authorName;
        this.authorImagePath = builder.authorImagePath;
        this.authorDescription = builder.authorDescription;
    }
    
    public static AuthorOfBookBuilder getBuilder() {
        return new AuthorOfBookBuilder();
    }
    
    public static class AuthorOfBookBuilder {
        private int bookId;
        private int authorId;
        private String authorName;
        private String authorImagePath;
        private String authorDescription;

        private AuthorOfBookBuilder() {
        }
        
        public AuthorOfBookBuilder BookId(int bookId) { this.bookId = bookId; return this; }
        public AuthorOfBookBuilder AuthorId(int authorId) { this.authorId = authorId; return this; }
        public AuthorOfBookBuilder AuthorName(String authorName) { this.authorName = authorName; return this; }
        public AuthorOfBookBuilder AuthorImagePath(String authorImagePath) { this.authorImagePath = authorImagePath; return this; }
        public AuthorOfBookBuilder AuthorDescription(String authorDescription) { this.authorDescription = authorDescription; return this; }
        public AuthorOfBook Build() {
            return new AuthorOfBook(this);
        }
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
