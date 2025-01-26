package com.fiap.hackathon.video.core.usecase;

import com.fiap.hackathon.video.app.adapter.output.queue.VideoReceivedDispatcher;
import com.fiap.hackathon.video.app.adapter.output.storage.FileStorage;
import com.fiap.hackathon.video.core.domain.Video;
import com.fiap.hackathon.video.core.domain.VideoStatus;
import com.fiap.hackathon.video.core.gateway.VideoGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class VideoCreateUseCase {

    private final VideoGateway videoGateway;
    private final FileStorage fileStorage;
    private final VideoReceivedDispatcher videoReceivedDispatcher;

    public Video execute(MultipartFile file) {

        Video video = new Video();

        video.setName(file.getOriginalFilename());
        video.setSize(file.getSize());
        video.setContentType(file.getContentType());
        video.setStatus(VideoStatus.RECEIVED);
        video.setCreatedAt(LocalDateTime.now());

        Video savedVideo = videoGateway.save(video);

        fileStorage.create(savedVideo, file);

        videoReceivedDispatcher.dispatch(savedVideo);

        return savedVideo;
    }

}
