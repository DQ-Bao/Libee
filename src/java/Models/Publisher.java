package Models;

public class Publisher {
    private int id;
    private String name;

    public Publisher() {
    }

    public Publisher(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public Publisher(PublisherBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
    }
    
    public static PublisherBuilder getBuilder() {
        return new PublisherBuilder();
    }
    
    public static class PublisherBuilder {
        private int id;
        private String name;

        private PublisherBuilder() {
        }
        
        public PublisherBuilder Id(int id) { this.id = id; return this; }
        public PublisherBuilder Name(String name) { this.name = name; return this; }
        public Publisher Build() {
            return new Publisher(this);
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
