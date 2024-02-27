package Models;

public class SubCategory {
    private int id;
    private String name;
    private int parentId;

    public SubCategory() {
    }

    public SubCategory(int id, String name, int parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }
    
    public SubCategory(SubCategoryBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.parentId = builder.parentId;
    }
    
    public static SubCategoryBuilder getBuilder() {
        return new SubCategoryBuilder();
    }
    
    public static class SubCategoryBuilder {
        private int id;
        private String name;
        private int parentId;

        private SubCategoryBuilder() {
        }
        
        public SubCategoryBuilder Id(int id) { this.id = id; return this; }
        public SubCategoryBuilder Name(String name) { this.name = name; return this; }
        public SubCategoryBuilder ParentId(int parentId) { this.parentId = parentId; return this; }
        public SubCategory Build() {
            return new SubCategory(this);
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
}
