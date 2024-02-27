package Models;

public class Category {
    private int id;
    private String name;

    public Category() {
    }

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public Category(CategoryBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
    }
    
    public static CategoryBuilder getBuilder() {
        return new CategoryBuilder();
    }
    
    public static class CategoryBuilder {
        private int id;
        private String name;
        
        private CategoryBuilder() {
        }
        
        public CategoryBuilder Id(int id) { this.id = id; return this; }
        public CategoryBuilder Name(String name) { this.name = name; return this; }
        public Category Build() {
            return new Category(this);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
