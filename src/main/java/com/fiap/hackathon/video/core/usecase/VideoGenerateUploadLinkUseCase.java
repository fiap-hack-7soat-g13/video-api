package com.fiap.hackathon.video.core.usecase;

import com.fiap.hackathon.video.app.adapter.output.storage.FileStorage;
import com.fiap.hackathon.video.app.adapter.output.storage.Location;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class VideoGenerateUploadLinkUseCase {

    private final FileStorage fileStorage;

    public String execute(UUID identifier) {
        return fileStorage.generateUploadLink(Location.UPLOAD, identifier.toString());
    }

}
