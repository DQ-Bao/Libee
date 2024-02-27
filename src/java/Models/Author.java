package Models;

public class Author {
    private int id;
    private String name;
    private String imagePath;
    private String description;

    public Author() {
    }

    public Author(int id, String name, String imagePath, String description) {
        this.id = id;
        this.name = name;
        this.imagePath = imagePath;
        this.description = description;
    }
    
    public Author(AuthorBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.imagePath = builder.imagePath;
        this.description = builder.description;
    }
    
    public static AuthorBuilder getBuilder() {
        return new AuthorBuilder();
    }
    
    public static class AuthorBuilder {
        private int id;
        private String name;
        private String imagePath;
        private String description;
        
        private AuthorBuilder() {
        }
        
        public AuthorBuilder Id(int id) { this.id = id; return this; }
        public AuthorBuilder Name(String name) { this.name = name; return this; }
        public AuthorBuilder ImagePath(String imagePath) { this.imagePath = imagePath; return this; }
        public AuthorBuilder Description(String description) { this.description = description; return this; }
        public Author Build() {
            return new Author(this);
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

    public String getImagePath() {
        return imagePath;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
