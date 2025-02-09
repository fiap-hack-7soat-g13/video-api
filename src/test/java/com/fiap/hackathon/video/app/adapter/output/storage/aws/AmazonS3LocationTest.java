package com.fiap.hackathon.video.app.adapter.output.storage.aws;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AmazonS3LocationTest {

	@Test
	void AmazonS3Location_shouldCreateLocationWithGivenBucket() {
		String bucket = "test-bucket";

		AmazonS3Location location = new AmazonS3Location(bucket);

		assertNotNull(location);
		assertEquals(bucket, location.getBucket());
	}

	@Test
	void AmazonS3Location_shouldHandleNullBucket() {
		AmazonS3Location location = new AmazonS3Location(null);

		assertNotNull(location);
		assertEquals(null, location.getBucket());
	}
}