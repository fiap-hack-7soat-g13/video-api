package com.fiap.hackathon.video.app.adapter.output.storage.aws;

import com.fiap.hackathon.video.app.adapter.output.storage.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.InputStreamSource;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AmazonS3FileStorageTest {

	private S3Client s3Client;
	private S3Presigner s3Presigner;
	private AmazonS3FileStorage amazonS3FileStorage;
	private final String videoBucket = "video-bucket";
	private final String thumbnailBucket = "thumbnail-bucket";

	@BeforeEach
	void setUp() {
		s3Client = mock(S3Client.class);
		s3Presigner = mock(S3Presigner.class);
		amazonS3FileStorage = new AmazonS3FileStorage(s3Client, s3Presigner, videoBucket, thumbnailBucket);
	}

	@Test
	void getVideoLocation_shouldReturnVideoLocation() {
		Location result = amazonS3FileStorage.getVideoLocation();

		assertNotNull(result);
		assertEquals(videoBucket, ((AmazonS3Location) result).getBucket());
	}

	@Test
	void getThumbnailLocation_shouldReturnThumbnailLocation() {
		Location result = amazonS3FileStorage.getThumbnailLocation();

		assertNotNull(result);
		assertEquals(thumbnailBucket, ((AmazonS3Location) result).getBucket());
	}
}
