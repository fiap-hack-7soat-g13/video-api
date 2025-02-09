package com.fiap.hackathon.video.core.usecase;

import com.fiap.hackathon.video.app.adapter.output.queue.VideoReceivedDispatcher;
import com.fiap.hackathon.video.app.adapter.output.storage.FileStorage;
import com.fiap.hackathon.video.app.adapter.output.storage.Location;
import com.fiap.hackathon.video.core.common.util.Files;
import com.fiap.hackathon.video.core.domain.User;
import com.fiap.hackathon.video.core.domain.Video;
import com.fiap.hackathon.video.core.domain.VideoStatus;
import com.fiap.hackathon.video.core.gateway.VideoGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class VideoCreateUseCase {

    private final VideoGateway videoGateway;
    private final FileStorage fileStorage;
    private final VideoReceivedDispatcher videoReceivedDispatcher;

    public Video execute(UUID identifier, User user) {

        return Files.createTempDirAndExecute(tempDir -> {

            Path target = tempDir.resolve(identifier.toString());

            fileStorage.download(Location.UPLOAD, identifier.toString(), target);

            Video video = new Video();

            video.setName("video.zip");
            video.setSize(target.toFile().length());
            video.setContentType(Files.detectContentType(target));
            video.setStatus(VideoStatus.RECEIVED);
            video.setCreatedAt(LocalDateTime.now());
            video.setCreatedBy(user.getId());
            video.setCreatedByEmail(user.getEmail());

            Video savedVideo = videoGateway.save(video);

            fileStorage.copy(Location.UPLOAD, identifier.toString(), Location.VIDEO, savedVideo.getId().toString());

            videoReceivedDispatcher.dispatch(savedVideo);

            return savedVideo;
        });
    }

}
