package com.fiap.hackathon.video.app.adapter.output.storage;

import org.springframework.core.io.InputStreamSource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorage {

    void create(Location location, String name, MultipartFile source);

    InputStreamSource download(Location location, String name);

    String generateDownloadLink(Location location, String name);

    Location getVideoLocation();

    Location getThumbnailLocation();

}
