package DataAccesses;

import Models.Product;
import Models.Category;
import DataAccesses.Internal.DataAccess;
import DataAccesses.Internal.DBProps;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class ProductDataAccess {
    private static ProductDataAccess INSTANCE;
    private DBProps props;
    private List<Product> cache;
    
    public static ProductDataAccess getInstance(DBProps props) {
        if (INSTANCE == null) {
            INSTANCE = new ProductDataAccess(props);
        }
        return INSTANCE;
    }
    
    private ProductDataAccess(DBProps props) {
        this.props = props;
        this.cache = new ArrayList<>();
    }
    
    private Product searchCacheById(int productId) {
        for (int i = 0; i < cache.size(); i++) {
            if (cache.get(i).getId() == productId) {
                return cache.get(i);
            }
        }
        return null;
    }
    
    public List<Product> getAll() {
        List<Product> list = new ArrayList<>();
        String sp = "{call spProduct_GetAll}";
        try (CallableStatement statement = DataAccess.getConnection(props).prepareCall(sp)) {
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
        Product product = searchCacheById(productId);
        if (product != null) {
            return product;
        }
        String sp = "{call spProduct_GetById(?)}";
        try (CallableStatement statement = DataAccess.getConnection(props).prepareCall(sp)) {
            statement.setInt("ProductId", productId);
            statement.execute();
            ResultSet res = statement.getResultSet();
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
    
    public List<Product> searchByName(String searchText) {
        List<Product> list = new ArrayList<>();
        String sp = "{call spProduct_SearchByName(?)}";
        try (CallableStatement statement = DataAccess.getConnection(props).prepareCall(sp)) {
            statement.setString("SearchText", searchText);
            statement.execute();
            ResultSet res = statement.getResultSet();
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
                cache.add(new Product(id, name, price, description, new Category(cid, cname), quantityInStock, imagePath));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public void addOne(Product product) {
        String sp = "{call spProduct_AddOne(?, ?, ?, ?, ?)}";
        try (CallableStatement statement = DataAccess.getConnection(props).prepareCall(sp)) {
            statement.setString("Name", product.getName());
            statement.setDouble("Price", product.getPrice());
            statement.setString("Description", product.getDescription());
            statement.setInt("QuantityInStock", product.getQuantityInStock());
            statement.setString("ImagePath", product.getImagePath());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cache.clear();
    }
    
    public void updateOne(Product product) {
        String sp = "{call spProduct_UpdateOne(?, ?, ?, ?, ?, ?)}";
        try (CallableStatement statement = DataAccess.getConnection(props).prepareCall(sp)) {
            statement.setInt("Id", product.getId());
            statement.setString("Name", product.getName());
            statement.setDouble("Price", product.getPrice());
            statement.setString("Description", product.getDescription());
            statement.setInt("QuantityInStock", product.getQuantityInStock());
            statement.setString("ImagePath", product.getImagePath());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cache.clear();
    }
    
    public void deleteOne(int productId) {
        String sp = "{call spProduct_DeleteOne(?)}";
        try (CallableStatement statement = DataAccess.getConnection(props).prepareCall(sp)) {
            statement.setInt("ProductId", productId);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cache.clear();
    }
}
