package com.fiap.hackathon.video.core.usecase;

import com.fiap.hackathon.video.app.adapter.output.storage.FileStorage;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ThumbnailDownloadUseCase {

    private final FileStorage fileStorage;

    public InputStreamSource execute(Long id) {
        return fileStorage.download(fileStorage.getThumbnailLocation(), id.toString());
    }

}
