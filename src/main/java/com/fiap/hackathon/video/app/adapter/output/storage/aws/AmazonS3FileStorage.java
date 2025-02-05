package com.fiap.hackathon.video.app.adapter.output.storage.aws;

import com.fiap.hackathon.video.app.adapter.output.storage.FileStorage;
import com.fiap.hackathon.video.app.adapter.output.storage.Location;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "application.storage.amazonS3.active", havingValue = "true")
public class AmazonS3FileStorage implements FileStorage {

    @Override
    public void create(Location location, String name, InputStreamSource source) {

    }

    @Override
    public InputStreamSource download(Location location, String name) {
        return null;
    }

    @Override
    public Location getVideoLocation() {
        return null;
    }

    @Override
    public Location getThumbnailLocation() {
        return null;
    }

}
