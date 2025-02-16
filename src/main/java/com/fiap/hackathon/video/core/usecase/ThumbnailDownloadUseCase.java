package com.fiap.hackathon.video.core.usecase;

import com.fiap.hackathon.video.app.adapter.output.storage.FileStorage;
import com.fiap.hackathon.video.app.adapter.output.storage.Location;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ThumbnailDownloadUseCase {

    private final FileStorage fileStorage;

    public String execute(Long id) {
        return fileStorage.generateDownloadUrl(Location.THUMBNAIL, id.toString());
    }

}
