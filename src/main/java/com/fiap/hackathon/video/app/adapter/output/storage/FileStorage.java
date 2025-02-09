package com.fiap.hackathon.video.app.adapter.output.storage;

import org.springframework.core.io.InputStreamSource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface FileStorage {

    void create(Location location, String name, MultipartFile source);

    void copy(Location sourceLocation, String sourceName, Location targetLocation, String targetName);

    InputStreamSource download(Location location, String name);

    void download(Location location, String name, Path target);

    String generateUploadUrl(Location location, String name);

    String generateDownloadLink(Location location, String name);

}
