package com.fiap.hackathon.video.app.adapter.output.storage;

import com.fiap.hackathon.video.core.domain.Video;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@Component
@ConditionalOnProperty(name = "application.storage.fileSystem.active", havingValue = "true")
public class FileSystemFileStorage implements FileStorage {

    private Path directory;

    public FileSystemFileStorage(@Value("${application.storage.fileSystem.directory}") String directory) {
        this.directory = Path.of(directory);
    }

    @PostConstruct
    public void post() throws IOException {
        Files.createDirectories(directory);
    }

    @Override
    public void create(Video video, MultipartFile file) {
        try (InputStream is = file.getInputStream(); OutputStream os = new BufferedOutputStream(new FileOutputStream(directory.resolve(video.getId().toString()).toFile()))) {
            IOUtils.copy(is, os);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
