package DataAccesses;

import Models.Product;
import Models.Category;
import DataAccesses.Internal.DataAccess;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class ProductDataAccess {
    private static ProductDataAccess INSTANCE;
    
    public static ProductDataAccess getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ProductDataAccess();
        }
        return INSTANCE;
    }
    
    private ProductDataAccess() {
    }
    
    public List<Product> getAll() {
        List<Product> list = new ArrayList<>();
        String sp = "{call spProduct_GetAll}";
        try (CallableStatement statement = DataAccess.getConnection().prepareCall(sp)) {
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                int id = res.getInt("Id");
                String name = res.getString("Name");
                double price = res.getDouble("Price");
                String description = res.getString("Description");
                int cid = res.getInt("CategoryId");
                String cname = res.getString("CategoryName");
                int quantityInStock = res.getInt("QuantityInStock");
                String imagePath = res.getString("ImagePath");
                list.add(new Product(id, name, price, description, new Category(cid, cname), quantityInStock, imagePath));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public Product getById(int productId) {
        String sp = "{call spProduct_GetById(?)}";
        Product product = null;
        try (CallableStatement statement = DataAccess.getConnection().prepareCall(sp)) {
            statement.setInt("ProductId", productId);
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                int id = res.getInt("Id");
                String name = res.getString("Name");
                double price = res.getDouble("Price");
                String description = res.getString("Description");
                int cid = res.getInt("CategoryId");
                String cname = res.getString("CategoryName");
                int quantityInStock = res.getInt("QuantityInStock");
                String imagePath = res.getString("ImagePath");
                product = new Product(id, name, price, description, new Category(cid, cname), quantityInStock, imagePath);
                break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }
    
    public void addOne(Product product) {
        String sp = "{call spProduct_AddOne(?, ?, ?, ?, ?)}";
        try (CallableStatement statement = DataAccess.getConnection().prepareCall(sp)) {
            statement.setString("Name", product.getName());
            statement.setDouble("Price", product.getPrice());
            statement.setString("Description", product.getDescription());
            statement.setInt("QuantityInStock", product.getQuantityInStock());
            statement.setString("ImagePath", product.getImagePath());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
