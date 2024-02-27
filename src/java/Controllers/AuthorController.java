package Controllers;

import Annotations.Authorize;
import Models.Author;
import Services.ProductService;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import java.util.Properties;

@MultipartConfig
public class AuthorController extends HttpServlet {
    private ProductService productSVC;

    @Override
    public void init() throws ServletException {
        this.productSVC = ProductService.getInstance();
    }
    
    @Override
    @Authorize({"Admin"})
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            resp.sendError(400);
            return;
        }
        if (action.equals("add-author")) {
            String name = req.getParameter("author-name");
            String description = req.getParameter("author-description");
            Part image = req.getPart("author-image");
            String imageName = null;
            if (image != null && image.getSize() != 0) {
                imageName = image.getSubmittedFileName();
                // TODO: Abstract properties in Configuration class
                Properties props = new Properties();
                InputStream in = req.getServletContext().getResourceAsStream("/WEB-INF/app.properties");
                props.load(in);
                String imageAbsolutePath = props.getProperty("file.image.location");
                String uploadPath = imageAbsolutePath + "/" + imageName;
                productSVC.saveImage(image.getInputStream(), uploadPath);
            }
            productSVC.addOneAuthor(Author
                    .getBuilder()
                    .Name(name)
                    .Description(description)
                    .ImagePath(imageName)
                    .Build());
        }
        resp.sendRedirect(req.getContextPath() + "/Admin");
    }
}
