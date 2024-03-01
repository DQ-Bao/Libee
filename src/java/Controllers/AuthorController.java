package Controllers;

import Annotations.Authorize;
import Models.Author;
import DataAccesses.AuthorDataAccess;
import DataAccesses.Internal.DBProps;
import Utils.ImageUtils;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@MultipartConfig
public class AuthorController extends HttpServlet {
    private static String IMAGE_LOCATION;
    private AuthorDataAccess authorDAO;

    @Override
    public void init() throws ServletException {
        String driverName = getServletContext().getInitParameter("db-driver");
        String connectionString = getServletContext().getInitParameter("db-connection-string");
        this.authorDAO = AuthorDataAccess.getInstance(new DBProps(driverName, connectionString));
        IMAGE_LOCATION = getServletContext().getInitParameter("image-location");
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
                imageName = ImageUtils.saveImage(image.getInputStream(), IMAGE_LOCATION);
            }
            authorDAO.addOne(Author
                    .getBuilder()
                    .Name(name)
                    .Description(description)
                    .ImagePath(imageName)
                    .Build());
        }
        resp.sendRedirect(req.getContextPath() + "/Admin");
    }
}
