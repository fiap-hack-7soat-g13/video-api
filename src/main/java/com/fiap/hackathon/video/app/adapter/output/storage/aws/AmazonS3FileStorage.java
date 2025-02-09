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
import software.amazon.awssdk.services.s3.model.CopyObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Map;

@Component
@ConditionalOnProperty(name = "application.storage.amazonS3.active", havingValue = "true")
public class AmazonS3FileStorage implements FileStorage {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;
    private final Map<Location, String> locations;

    public AmazonS3FileStorage(S3Client s3Client,
                               S3Presigner s3Presigner,
                               @Value("${application.storage.amazonS3.uploadBucket}") String uploadBucket,
                               @Value("${application.storage.amazonS3.videoBucket}") String videoBucket,
                               @Value("${application.storage.amazonS3.thumbnailBucket}") String thumbnailBucket) {
        this.s3Client = s3Client;
        this.s3Presigner = s3Presigner;
        this.locations = Map.of(
                Location.UPLOAD, uploadBucket,
                Location.VIDEO, videoBucket,
                Location.THUMBNAIL, thumbnailBucket
        );
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
    public void copy(Location sourceLocation, String sourceName, Location targetLocation, String targetName) {
        CopyObjectRequest copyObjectRequest = CopyObjectRequest.builder()
                .sourceBucket(getBucket(sourceLocation))
                .sourceKey(sourceName)
                .destinationBucket(getBucket(targetLocation))
                .destinationKey(targetName)
                .build();
        s3Client.copyObject(copyObjectRequest);
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
    public void download(Location location, String name, Path target) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(getBucket(location))
                .key(name)
                .build();
        s3Client.getObject(getObjectRequest, target);
    }

    @Override
    public String generateUploadUrl(Location location, String name) {

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(getBucket(location))
                .key(name)
                .build();

        PutObjectPresignRequest putObjectPresignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(10))
                .putObjectRequest(putObjectRequest)
                .build();

        PresignedPutObjectRequest presignedPutObjectRequest = s3Presigner.presignPutObject(putObjectPresignRequest);

        return presignedPutObjectRequest.url().toString();
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

    private String getBucket(Location location) {
        return locations.get(location);
    }

}
