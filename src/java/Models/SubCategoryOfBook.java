package Models;

public class SubCategoryOfBook {
    private int bookId;
    private int subCategoryId;
    private String subCategoryName;
    private boolean primary;

    public SubCategoryOfBook() {
    }

    public SubCategoryOfBook(int bookId, int subCategoryId, String subCategoryName, boolean primary) {
        this.bookId = bookId;
        this.subCategoryId = subCategoryId;
        this.subCategoryName = subCategoryName;
        this.primary = primary;
    }
    
    public SubCategoryOfBook(SubCategoryOfBookBuilder builder) {
        this.bookId = builder.bookId;
        this.subCategoryId = builder.subCategoryId;
        this.subCategoryName = builder.subCategoryName;
        this.primary = builder.primary;
    }
    
    public static SubCategoryOfBookBuilder getBuilder() {
        return new SubCategoryOfBookBuilder();
    }
    
    public static class SubCategoryOfBookBuilder {
        private int bookId;
        private int subCategoryId;
        private String subCategoryName;
        private boolean primary = false;

        private SubCategoryOfBookBuilder() {
        }
        
        public SubCategoryOfBookBuilder BookId(int bookId) { this.bookId = bookId; return this; }
        public SubCategoryOfBookBuilder SubCategoryId(int subCategoryId) { this.subCategoryId = subCategoryId; return this; }
        public SubCategoryOfBookBuilder SubCategoryName(String subCategoryName) { this.subCategoryName = subCategoryName; return this; }
        public SubCategoryOfBookBuilder Primary(boolean primary) { this.primary = primary; return this; }
        public SubCategoryOfBook Build() {
            return new SubCategoryOfBook(this);
        }
    }

    public int getBookId() {
        return bookId;
    }

    public int getSubCategoryId() {
        return subCategoryId;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public boolean isPrimary() {
        return primary;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public void setSubCategoryId(int subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }
}
