package com.taekyeong.tkgram.util;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Configuration
public class S3Uploader {
    private AmazonS3 amazonS3;

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    private static final int THUMBNAIL_WIDTH = 161;
    private static final int THUMBNAIL_HEIGHT = 161;

    @PostConstruct
    public void setS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);

        amazonS3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(this.region)
                .build();
    }

    public String uploadThumbnail(MultipartFile file) throws IOException {

        String fileName = UUID.randomUUID() + ".png";
        InputStream thumbnail = Base64ToMultipartFile.resizeImage(file.getInputStream(),
                THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT, "png");
        amazonS3.putObject(new PutObjectRequest(bucket, fileName, thumbnail, null)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    public List<String> uploadImages(List<MultipartFile> files) throws IOException {
        List<String> imageUrls = new ArrayList<>();
        for(MultipartFile file : files) {
            String fileName = UUID.randomUUID() + ".png";

            amazonS3.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(), null)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            String url = amazonS3.getUrl(bucket, fileName).toString();
            imageUrls.add(url);
        }
        return imageUrls;
    }

    public String uploadImage(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID() + ".png";

        amazonS3.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(), null)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    public boolean existImage(String path) {
        return amazonS3.doesObjectExist(bucket, path);
    }

    public void deleteImage(String path) {
        amazonS3.deleteObject(bucket, path);
    }
}