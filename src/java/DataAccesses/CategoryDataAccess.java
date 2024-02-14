package DataAccesses;

import Models.Category;
import Models.SubCategoryOfBook;
import DataAccesses.Internal.DataAccess;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryDataAccess {
    private static CategoryDataAccess INSTANCE = null;
    
    public static CategoryDataAccess getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CategoryDataAccess();
        }
        return INSTANCE;
    }
    
    private CategoryDataAccess() {
    }
    
    public List<Category> getAll() {
        String sql = "select * from Category;";
        List<Category> publishers = new ArrayList<>();
        try (Connection conn = DataAccess.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql)) {
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                int id = res.getInt("Id");
                String name = res.getString("Name");
                publishers.add(new Category(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return publishers;
    }
    
    public List<SubCategoryOfBook> getSubCategoriesOfBook(int bookId) {
        List<SubCategoryOfBook> list = new ArrayList<>();
        String sp = "{call spBook_GetSubCategories(?)}";
        try (CallableStatement statement = DataAccess.getConnection().prepareCall(sp)) {
            statement.setInt("BookId", bookId);
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                int id = res.getInt("Id");
                String name = res.getString("Name");
                boolean primary = res.getBoolean("Primary");
                list.add(new SubCategoryOfBook(bookId, id, name, primary));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public Category getById(int cateId) {
        String sql = "select * from Category where Id = ?;";
        Category cate = null;
        try (Connection conn = DataAccess.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, cateId);
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                int id = res.getInt("Id");
                String name = res.getString("Name");
                cate = new Category(id, name);
                break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cate;
    }
    
    public boolean addOne(String cateName) {
        String sql = "insert into Category([Name]) values (?);";
        boolean success = false;
        try (Connection conn = DataAccess.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, cateName);
            if (statement.executeUpdate() == 1) {
                success = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }
    
    public boolean updateOne(Category cate) {
        String sql = "update Category set [Name] = ? where [Id] = ?;";
        boolean success = false;
        try (Connection conn = DataAccess.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, cate.getName());
            statement.setInt(2, cate.getId());
            if (statement.executeUpdate() == 1) {
                success = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }
    
    public boolean deleteOne(int cateId) {
        String sql = "delete from Category where [Id] = ?;";
        boolean success = false;
        try (Connection conn = DataAccess.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, cateId);
            if (statement.executeUpdate() == 1) {
                success = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }
}
