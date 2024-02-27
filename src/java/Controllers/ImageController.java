package Controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Properties;

public class ImageController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        Properties props = new Properties();
        InputStream in = req.getServletContext().getResourceAsStream("/WEB-INF/app.properties");
        props.load(in);
        String imageAbsolutePath = props.getProperty("file.image.location");
        String imageName = req.getPathInfo().substring(1);
        File file = new File(imageAbsolutePath, imageName);
        if (!file.exists()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        resp.setHeader("Content-Type", req.getServletContext().getMimeType(imageName));
        resp.setHeader("Content-Length", String.valueOf(file.length()));
        Files.copy(file.toPath(), resp.getOutputStream());
    }
}
