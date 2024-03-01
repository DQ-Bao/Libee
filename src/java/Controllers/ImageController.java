package Controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ImageController extends HttpServlet {
    private static String IMAGE_LOCATION;

    @Override
    public void init() throws ServletException {
        IMAGE_LOCATION = getServletContext().getInitParameter("image-location");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        String imageName = req.getPathInfo().substring(1);
        File file = new File(IMAGE_LOCATION, imageName);
        if (!file.exists()) {
            resp.sendError(404);
            return;
        }
        resp.setHeader("Content-Type", req.getServletContext().getMimeType(imageName));
        resp.setHeader("Content-Length", String.valueOf(file.length()));
        Files.copy(file.toPath(), resp.getOutputStream());
    }
}
