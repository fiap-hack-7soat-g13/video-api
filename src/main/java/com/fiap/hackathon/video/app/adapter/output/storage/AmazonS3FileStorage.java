package com.fiap.hackathon.video.app.adapter.output.storage;

import com.fiap.hackathon.video.core.domain.Video;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

@Component
@ConditionalOnProperty(name = "application.storage.amazonS3.active", havingValue = "true")
public class AmazonS3FileStorage implements FileStorage {

    @Override
    public void create(Video video, MultipartFile file) {

    }

}
