package com.example.Ecommerce.ultils;


import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;



@Component
public class FileUploadUtil {

    public String uploadFile(MultipartFile file) throws IOException {

        String fileName = file.getOriginalFilename();

        String uploadFolder = System.getProperty("user.dir") + "/Ecommerce/src/main/resources/upload";

        String filePath = uploadFolder + "/" + fileName;

        file.transferTo(new File(filePath));

        String domain = "http://localhost:8080";

        return fileName;
    }


}
