package com.example.Ecommerce.common.File;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
public class S3Service {

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    public String generateUrl(String filename, HttpMethod http){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE,1);
        URL url = amazonS3.generatePresignedUrl(bucketName,filename,calendar.getTime(),http);
        return url.toString();
    }

    public String uploadFile(MultipartFile file) {
        try {
            String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            amazonS3.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), metadata));
            return amazonS3.getUrl(bucketName, fileName).toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file to S3", e);
        }
    }
}
