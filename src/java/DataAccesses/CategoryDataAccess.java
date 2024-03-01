package DataAccesses;

import Models.Category;
import Models.SubCategory;
import Models.SubCategoryOfBook;
import DataAccesses.Internal.DataAccess;
import DataAccesses.Internal.DBProps;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryDataAccess {
    private static CategoryDataAccess INSTANCE = null;
    private DBProps props;
    
    public static CategoryDataAccess getInstance(DBProps props) {
        if (INSTANCE == null) {
            INSTANCE = new CategoryDataAccess(props);
        }
        return INSTANCE;
    }
    
    private CategoryDataAccess(DBProps props) {
        this.props = props;
    }
    
    public List<Category> getAllCategories() {
        String sp = "{call spCategory_GetAll}";
        List<Category> categories = new ArrayList<>();
        try (CallableStatement statement = DataAccess.getConnection(props).prepareCall(sp)) {
            statement.execute();
            ResultSet res = statement.getResultSet();
            while (res.next()) {
                int id = res.getInt("Id");
                String name = res.getString("Name");
                categories.add(new Category(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }
    
    public List<SubCategory> getSubCategories(String categoryName) {
        String sp = "{call spCategory_GetSubCategories(?)}";
        List<SubCategory> subCategories = new ArrayList<>();
        try (CallableStatement statement = DataAccess.getConnection(props).prepareCall(sp)) {
            statement.setString("CategoryName", categoryName);
            statement.execute();
            ResultSet res = statement.getResultSet();
            while (res.next()) {
                int id = res.getInt("Id");
                String name = res.getString("Name");
                int parentId = res.getInt("CategoryId");
                subCategories.add(new SubCategory(id, name, parentId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subCategories;
    }
    
    public List<SubCategoryOfBook> getSubCategoriesOfBook(int bookId) {
        List<SubCategoryOfBook> list = new ArrayList<>();
        String sp = "{call spBook_GetSubCategories(?)}";
        try (CallableStatement statement = DataAccess.getConnection(props).prepareCall(sp)) {
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
        try (Connection conn = DataAccess.getConnection(props);
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
    
    public boolean updateOne(Category cate) {
        String sql = "update Category set [Name] = ? where [Id] = ?;";
        boolean success = false;
        try (Connection conn = DataAccess.getConnection(props);
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
        try (Connection conn = DataAccess.getConnection(props);
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

    public void addOneCategory(Category category) {
        String sp = "{call spCategory_AddOne(?)}";
        try (CallableStatement statement = DataAccess.getConnection(props).prepareCall(sp)) {
            statement.setString("Name", category.getName());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addOneSubCategory(SubCategory subCategory) {
        String sp = "{call spSubCategory_AddOne(?, ?)}";
        try (CallableStatement statement = DataAccess.getConnection(props).prepareCall(sp)) {
            statement.setString("Name", subCategory.getName());
            statement.setInt("CategoryId", subCategory.getParentId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
