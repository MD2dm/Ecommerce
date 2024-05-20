package com.example.Ecommerce.service.File;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    @Autowired
    private final S3Client s3Client;

    @Value("${bucket.name}")
    private String bucketName;

    public String upload(MultipartFile avatarFile) throws IOException {
        try {
            String key = generateUniqueKey(avatarFile.getOriginalFilename());
            String fileUrl = String.format("https://%s.s3.amazonaws.com/%s", bucketName, key);
            s3Client.putObject(PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build(), RequestBody.fromBytes(avatarFile.getBytes()));
            return fileUrl;
        } catch (S3Exception e) {
            throw new IOException("Failed to upload avatar to S3", e);
        }
    }

    public InputStream downloadFile(String key) throws IOException {
        try {
            ResponseBytes<GetObjectResponse> objectResponse = s3Client.getObject(GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build(), ResponseTransformer.toBytes());

            return objectResponse.asInputStream();
        } catch (S3Exception e) {
            throw new IOException("Failed to download file from S3", e);
        }
    }

    private String generateUniqueKey(String originalFilename) {
        return UUID.randomUUID().toString() + "_" + originalFilename;
    }
}
