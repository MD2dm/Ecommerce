package com.example.Ecommerce.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class AmazonS3Config {

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.AP_SOUTHEAST_2)
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(
                                "AKIAZI2LI6F5ON6UWEG5",
                                "ugHz0uSkbrbnwpomFG6Cl+xB7TeVDP0JJz8rLAas")))
                .build();
    }
}
//@Value("${aws.access.key.id}")
//private String accessKeyId;
//
//@Value("${aws.access.key.secret}")
//private String accessKeySecret;
//
//@Value("${aws.region}")
//private String region;
//
//@Bean
//public AmazonS3 amazonS3Client() {
//    AWSCredentials credentials = new BasicAWSCredentials(accessKeyId, accessKeySecret);
//    return AmazonS3ClientBuilder.standard()
//            .withCredentials(new AWSStaticCredentialsProvider(credentials))
//            .withRegion(region)
//            .build();
//}
