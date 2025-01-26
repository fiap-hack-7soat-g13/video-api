package com.fiap.hackathon.video.app.adapter.output.storage;

import com.fiap.hackathon.video.core.domain.Video;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorage {

    void create(Video video, MultipartFile file);

}
