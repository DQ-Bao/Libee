package Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class ImageUtils {
    public static String saveImage(InputStream image, String uploadPath) 
            throws IOException {
        String imageName = generateImageName();
        File file = new File(uploadPath, imageName);
        OutputStream fout = new FileOutputStream(file);
        final byte[] buf = new byte[1024];
        int len;
        while ((len = image.read(buf)) != -1) {
            fout.write(buf, 0, len);
        }
        image.close();
        fout.close();
        return imageName;
    }
    
    public static void deleteImage(String imagePath) {
        if (imagePath == null || imagePath.isBlank()) return;
        File file = new File(imagePath);
        if (!file.exists()) return;
        file.delete();
    }
    
    public static String generateImageName() {
        UUID uuid = UUID.randomUUID();
        return "img_" + uuid.toString() + ".jpg";
    }
}
