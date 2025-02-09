package com.fiap.hackathon.video.app.adapter.output.storage.aws;

import com.fiap.hackathon.video.app.adapter.output.storage.FileStorage;
import com.fiap.hackathon.video.app.adapter.output.storage.Location;
import com.fiap.hackathon.video.core.common.util.Files;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.IOException;
import java.time.Duration;

@Component
@ConditionalOnProperty(name = "application.storage.amazonS3.active", havingValue = "true")
public class AmazonS3FileStorage implements FileStorage {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;
    private final AmazonS3Location videoLocation;
    private final AmazonS3Location thumbnailLocation;

    public AmazonS3FileStorage(S3Client s3Client,
                               S3Presigner s3Presigner,
                               @Value("${application.storage.amazonS3.videoBucket}") String videoBucket,
                               @Value("${application.storage.amazonS3.thumbnailBucket}") String thumbnailBucket) {
        this.s3Client = s3Client;
        this.s3Presigner = s3Presigner;
        this.videoLocation = new AmazonS3Location(videoBucket);
        this.thumbnailLocation = new AmazonS3Location(thumbnailBucket);
    }

    @Override
    public void create(Location location, String name, MultipartFile source) {
        Files.createTempFileAndExecute(tempFile -> {
            try {
                source.transferTo(tempFile);
                PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                        .bucket(getBucket(location))
                        .key(name)
                        .build();
                s3Client.putObject(putObjectRequest, tempFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public InputStreamSource download(Location location, String name) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(getBucket(location))
                .key(name)
                .build();
        return new InputStreamResource(s3Client.getObjectAsBytes(getObjectRequest).asInputStream());
    }

    @Override
    public String generateDownloadLink(Location location, String name) {

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(getBucket(location))
                .key(name)
                .build();

        GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(1))
                .getObjectRequest(getObjectRequest)
                .build();

        PresignedGetObjectRequest presignedGetObjectRequest = s3Presigner.presignGetObject(getObjectPresignRequest);

        return presignedGetObjectRequest.url().toString();
    }

    @Override
    public Location getVideoLocation() {
        return videoLocation;
    }

    @Override
    public Location getThumbnailLocation() {
        return thumbnailLocation;
    }

    private String getBucket(Location location) {
        return ((AmazonS3Location) location).getBucket();
    }

}
