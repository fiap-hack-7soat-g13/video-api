package com.fiap.hackathon.video.app.adapter.output.storage;

import org.springframework.core.io.InputStreamSource;

public interface FileStorage {

    void create(Location location, String name, InputStreamSource source);

    InputStreamSource download(Location location, String name);

    Location getVideoLocation();

    Location getThumbnailLocation();

}
