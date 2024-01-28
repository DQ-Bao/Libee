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

    public int getId() {
        return id;
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
