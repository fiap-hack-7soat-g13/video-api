package com.fiap.hackathon.video.app.adapter.output.storage;

import java.nio.file.Path;

public interface FileStorage {

    String generateUploadUrl(Location location, String name);

    String generateDownloadUrl(Location location, String name);

    void download(Location location, String name, Path target);

    void copy(Location sourceLocation, String sourceName, Location targetLocation, String targetName);

}
