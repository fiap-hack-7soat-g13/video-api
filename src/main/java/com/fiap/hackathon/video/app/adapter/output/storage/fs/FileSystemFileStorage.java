package com.fiap.hackathon.video.app.adapter.output.storage.fs;

import com.fiap.hackathon.video.app.adapter.output.storage.FileStorage;
import com.fiap.hackathon.video.app.adapter.output.storage.Location;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

@Component
@ConditionalOnProperty(name = "application.storage.fileSystem.active", havingValue = "true")
public class FileSystemFileStorage implements FileStorage {

    private final Map<Location, Path> locations;

    public FileSystemFileStorage(@Value("${application.storage.fileSystem.uploadDirectory}") String uploadDirectory,
                                 @Value("${application.storage.fileSystem.videoDirectory}") String videoDirectory,
                                 @Value("${application.storage.fileSystem.thumbnailDirectory}") String thumbnailDirectory) {
        this.locations = Map.of(
                Location.UPLOAD, Path.of(uploadDirectory),
                Location.VIDEO, Path.of(videoDirectory),
                Location.THUMBNAIL, Path.of(thumbnailDirectory)
        );
    }

    @PostConstruct
    public void postConstruct() throws IOException {
        for (Path path : locations.values()) {
            Files.createDirectories(path);
        }
    }

    @Override
    public String generateUploadUrl(Location location, String name) {
        return null;
    }

    @Override
    public String generateDownloadUrl(Location location, String name) {
        return null;
    }

    @Override
    public void download(Location location, String name, Path target) {
        try {
            Path source = getPath(location, name);
            Files.copy(source, target);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void copy(Location sourceLocation, String sourceName, Location targetLocation, String targetName) {
        Path source = getPath(sourceLocation, sourceName);
        Path target = getPath(targetLocation, targetName);
        try {
            Files.copy(source, target);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Path getPath(Location location, String name) {
        return locations.get(location).resolve(name);
    }

}
