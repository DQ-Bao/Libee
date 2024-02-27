package Models;

import Models.Category.CategoryBuilder;

public class Product {
    protected int id;
    protected String name;
    protected double price;
    protected String description;
    protected Category category;
    protected int quantityInStock;
    protected String imagePath;

    public Product() {
    }
    
    public Product(ProductBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.price = builder.price;
        this.description = builder.description;
        this.category = builder.category;
        this.quantityInStock = builder.quantityInStock;
        this.imagePath = builder.imagePath;
    }
    
    public static ProductBuilder getBuilder() {
        return new ProductBuilder();
    }

    public Product(int id, String name, double price, String description, Category category, int quantityInStock, String imagePath) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
        this.quantityInStock = quantityInStock;
        this.imagePath = imagePath;
    }

    public static class ProductBuilder<T extends ProductBuilder<T>> {
        protected int id;
        protected String name;
        protected double price;
        protected String description;
        protected Category category;
        protected int quantityInStock;
        protected String imagePath;
        
        protected ProductBuilder() {
        }
        
        public T Id(int id) { this.id = id; return (T)this; }
        public T Name(String name) { this.name = name; return (T)this; }
        public T Price(double price) { this.price = price; return (T)this; }
        public T Description(String description) { this.description = description; return (T)this; }
        public T Category(CategoryBuilder builder) { this.category = builder.Build(); return (T)this; }
        public T QuantityInStock(int quantityInStock) { this.quantityInStock = quantityInStock; return (T)this; }
        public T ImagePath(String imagePath) { this.imagePath = imagePath; return (T)this; }
        public Product Build() {
            return new Product(this);
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
