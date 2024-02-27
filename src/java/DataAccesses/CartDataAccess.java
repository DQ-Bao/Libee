package DataAccesses;

import Models.Cart;
import Models.Cart.CartBuilder;
import Models.CartItem;
import Models.Product;
import Models.Category;
import DataAccesses.Internal.DataAccess;
import java.time.LocalDateTime;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CartDataAccess {
    private static CartDataAccess INSTANCE;
    
    public static CartDataAccess getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CartDataAccess();
        }
        return INSTANCE;
    }
    
    private CartDataAccess() {
    }
    
    public Cart getActiveCartOfUser(int userId) {
        String sp = "{call spCart_GetActiveCartOfUser(?)}";
        Cart cart = null;
        
        try (CallableStatement statement = DataAccess.getConnection().prepareCall(sp)) {
            statement.setInt("UserId", userId);
            statement.execute();
            ResultSet res = statement.getResultSet();
            CartBuilder builder = null;
            while (res.next()) {
                int id = res.getInt("Id");
                int uid = res.getInt("UserId");
                LocalDateTime createdDate = res.getTimestamp("CreatedDate").toLocalDateTime();
                double total = res.getDouble("Total");
                builder = Cart.getBuilder()
                        .Id(id)
                        .UserId(uid)
                        .CreatedDate(createdDate)
                        .Total(total);
                break;
            }
            
            if (builder == null) {
                return null;
            }
            
            statement.getMoreResults();
            res = statement.getResultSet();
            while (res.next()) {
                int cartId = res.getInt("CartId");
                int productId = res.getInt("ProductId");
                String productName = res.getString("Name");
                double price = res.getDouble("Price");
                String description = res.getString("Description");
                int categoryId = res.getInt("CategoryId");
                String categoryName = res.getString("CategoryName");
                int quantityInStock = res.getInt("QuantityInStock");
                String imagePath = res.getString("ImagePath");
                int quantity = res.getInt("Quantity");
                double purchasePrice = res.getDouble("PurchasePrice");
                builder.Item(CartItem
                        .getBuilder()
                        .CartId(cartId)
                        .Product(Product
                                .getBuilder()
                                .Id(productId)
                                .Name(productName)
                                .Price(price)
                                .Description(description)
                                .Category(Category
                                        .getBuilder()
                                        .Id(categoryId)
                                        .Name(categoryName))
                                .QuantityInStock(quantityInStock)
                                .ImagePath(imagePath))
                        .Quantity(quantity)
                        .PurchasePrice(purchasePrice));
            }
            cart = builder.Build();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return cart;
    }
    
    public void addOneItem(int cartId, int productId, int quantity, double purchasePrice) {
        String sp = "{call spCart_AddOneItem(?, ?, ?, ?)}";
        try (CallableStatement statement = DataAccess.getConnection().prepareCall(sp)) {
            statement.setInt("CartId", cartId);
            statement.setInt("ProductId", productId);
            statement.setInt("Quantity", quantity);
            statement.setDouble("PurchasePrice", purchasePrice);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void calcTotal(int cartId) {
        String sp = "{call spCart_CalcTotal(?)}";
        try (CallableStatement statement = DataAccess.getConnection().prepareCall(sp)) {
            statement.setInt("CartId", cartId);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
