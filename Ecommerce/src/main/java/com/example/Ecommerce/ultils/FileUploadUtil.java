package com.example.Ecommerce.ultils;


import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;



@Component
public class FileUploadUtil {

    public String uploadFile(MultipartFile file) throws IOException {

        String fileName = file.getOriginalFilename();

        String uploadFolder = System.getProperty("user.dir") + "/Ecommerce/src/main/resources/upload";

        String filePath = uploadFolder + "/" + fileName;

        file.transferTo(new File(filePath));

        // Resize and save file
        BufferedImage originalImage = ImageIO.read(new File(filePath));
        BufferedImage resizedImage = getResizedImage(originalImage, 100, 100);
        ImageIO.write(resizedImage, "jpg", new File(filePath));

        String domain = "http://localhost:8080";

        return domain + "/" + fileName;
    }


    private BufferedImage getResizedImage(BufferedImage originalImage, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }
}
