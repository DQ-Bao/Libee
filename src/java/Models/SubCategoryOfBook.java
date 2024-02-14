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
