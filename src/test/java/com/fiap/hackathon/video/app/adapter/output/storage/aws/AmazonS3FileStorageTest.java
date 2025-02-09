package com.fiap.hackathon.video.app.adapter.output.storage.aws;

import org.junit.jupiter.api.BeforeEach;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import static org.mockito.Mockito.mock;

class AmazonS3FileStorageTest {

    private S3Client s3Client;
    private S3Presigner s3Presigner;
    private AmazonS3FileStorage amazonS3FileStorage;
    private final String uploadBucket = "upload-bucket";
    private final String videoBucket = "video-bucket";
    private final String thumbnailBucket = "thumbnail-bucket";

    @BeforeEach
    void setUp() {
        s3Client = mock(S3Client.class);
        s3Presigner = mock(S3Presigner.class);
        amazonS3FileStorage = new AmazonS3FileStorage(s3Client, s3Presigner, uploadBucket, videoBucket, thumbnailBucket);
    }

}
