package Listeners;

import Models.Category;
import Models.SubCategory;
import DataAccesses.CategoryDataAccess;
import DataAccesses.Internal.DBProps;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class CategoryListener implements ServletContextListener {
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String driverName = sce.getServletContext().getInitParameter("db-driver");
        String connectionString = sce.getServletContext().getInitParameter("db-connection-string");
        CategoryDataAccess dao = CategoryDataAccess.getInstance(new DBProps(driverName, connectionString));
        Map<String, List<SubCategory>> map = new HashMap<>();
        for (Category c : dao.getAllCategories()) {
            map.put(c.getName(), dao.getSubCategories(c.getName()));
        }
        sce.getServletContext().setAttribute("navCatMap", map);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        sce.getServletContext().removeAttribute("navCatMap");
    }
}
