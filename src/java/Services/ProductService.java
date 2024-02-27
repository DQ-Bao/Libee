package Services;

import DataAccesses.ProductDataAccess;
import DataAccesses.AuthorDataAccess;
import DataAccesses.BookDataAccess;
import DataAccesses.CategoryDataAccess;
import DataAccesses.PublisherDataAccess;
import Models.Author;
import Models.Category;
import Models.Publisher;
import Models.SubCategory;
import Models.Book;
import Models.Product;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class ProductService {
    private static ProductService INSTANCE;
    private ProductDataAccess productDAO;
    private CategoryDataAccess categoryDAO;
    private PublisherDataAccess publisherDAO;
    private AuthorDataAccess authorDAO;
    private BookDataAccess bookDAO;
    
    private List<Product> products;
    private List<Category> categories;
    private List<Publisher> publishers;
    private Map<String, List<SubCategory>> subCategoryCache;
    private List<Author> authors;
    
    public static ProductService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ProductService();
        }
        return INSTANCE;
    }
    
    private ProductService() {
        this.productDAO = ProductDataAccess.getInstance();
        this.categoryDAO = CategoryDataAccess.getInstance();
        this.publisherDAO = PublisherDataAccess.getInstance();
        this.authorDAO = AuthorDataAccess.getInstance();
        this.bookDAO = BookDataAccess.getInstance();
        this.products = new ArrayList<>();
        this.categories = new ArrayList<>();
        this.publishers = new ArrayList<>();
        this.authors = new ArrayList<>();
        this.subCategoryCache = new HashMap<>();
    }
    
    public List<Product> getAllProducts() {
        if (products == null || products.isEmpty()) {
            products = productDAO.getAll();
        }
        return products;
    }
    
    public List<Category> getAllCategories() {
        if (categories == null || categories.isEmpty()) {
            categories = categoryDAO.getAll();
        }
        return categories;
    }
    
    public List<SubCategory> getSubCategories(String categoryName) {
        if (subCategoryCache == null || !subCategoryCache.containsKey(categoryName)) {
            subCategoryCache.put(categoryName, categoryDAO.getSubCategories(categoryName));
        }
        return subCategoryCache.get(categoryName);
    }
    
    public List<Publisher> getAllBookPublishers() {
        if (publishers == null || publishers.isEmpty()) {
            publishers = publisherDAO.getAll();
        }
        return publishers;
    }
    
    public List<Author> getAllBookAuthors() {
        if (authors == null || authors.isEmpty()) {
            authors = authorDAO.getAll();
        }
        return authors;
    }
    
    public void addOneProduct(Product product) {
        if (product == null) return;
        productDAO.addOne(product);
        products.clear();
    }
    
    public void addOneBook(Book book) {
        if (book == null) return;
        bookDAO.addOne(book);
        products.clear();
    }
    
    public void addOneCategory(Category category) {
        if (category == null) return;
        categoryDAO.addOneCategory(category);
        categories.clear();
    }
    
    public void addOneSubCategory(SubCategory subCategory) {
        if (subCategory == null) return;
        categoryDAO.addOneSubCategory(subCategory);
        subCategoryCache.clear();
    }
    
    public void addOneAuthor(Author author) {
        if (author == null) return;
        authorDAO.addOne(author);
        authors.clear();
    }
    
    public void addOnePublisher(Publisher publisher) {
        if (publisher == null) return;
        publisherDAO.addOne(publisher);
        publishers.clear();
    }
    
    public void saveImage(InputStream image, String uploadPath) 
            throws IOException {
        File file = new File(uploadPath);
        OutputStream fout = new FileOutputStream(file);
        final byte[] buf = new byte[1024];
        int len;
        while ((len = image.read(buf)) != -1) {
            fout.write(buf, 0, len);
        }
        image.close();
        fout.close();
    }
}
