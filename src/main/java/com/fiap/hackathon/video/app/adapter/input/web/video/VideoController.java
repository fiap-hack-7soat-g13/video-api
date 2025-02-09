package com.fiap.hackathon.video.app.adapter.input.web.video;

import com.fiap.hackathon.video.app.adapter.input.web.video.dto.CreateVideoRequest;
import com.fiap.hackathon.video.app.adapter.input.web.video.dto.VideoResponse;
import com.fiap.hackathon.video.app.adapter.input.web.video.dto.VideoUploadUrlResponse;
import com.fiap.hackathon.video.app.adapter.input.web.video.mapper.VideoResponseMapper;
import com.fiap.hackathon.video.core.common.exception.NotFoundException;
import com.fiap.hackathon.video.core.domain.User;
import com.fiap.hackathon.video.core.domain.Video;
import com.fiap.hackathon.video.core.usecase.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class VideoController {

    private final VideoGenerateUploadUrlUseCase videoGenerateUploadUrlUseCase;
    private final VideoCreateUseCase videoCreateUseCase;
    private final VideoGetUseCase videoGetUseCase;
    private final VideoListUseCase videoListUseCase;
    private final ThumbnailDownloadUseCase thumbnailDownloadUseCase;
    private final VideoResponseMapper videoResponseMapper;

    @PutMapping
    public VideoUploadUrlResponse generateUploadUrl() {
        UUID id = UUID.randomUUID();
        String url = videoGenerateUploadUrlUseCase.execute(id);
        return new VideoUploadUrlResponse(id, url);
    }

    @PostMapping
    public VideoResponse create(@RequestBody CreateVideoRequest request) {
        User user = assertAuthenticatedUser();
        Video video = videoCreateUseCase.execute(request.getUploadId(), request.getFileName(), user);
        return videoResponseMapper.toVideoResponse(video);
    }

    @GetMapping("/{id}")
    public VideoResponse get(@PathVariable Long id) {

        User user = assertAuthenticatedUser();
        Video video = videoGetUseCase.execute(id);

        if (!Objects.equals(video.getCreatedBy(), user.getId())) {
            throw new NotFoundException();
        }

        return videoResponseMapper.toVideoResponse(video);
    }

    @GetMapping
    public List<VideoResponse> list() {
        User user = assertAuthenticatedUser();
        List<Video> videos = videoListUseCase.execute(user.getId());
        return videos.stream().map(videoResponseMapper::toVideoResponse).toList();
    }

    @GetMapping("/{id}/thumbnail")
    public ResponseEntity<Void> thumbnailDownload(@PathVariable Long id) {

        User user = assertAuthenticatedUser();
        Video video = videoGetUseCase.execute(id);

        if (!Objects.equals(video.getCreatedBy(), user.getId())) {
            throw new NotFoundException();
        }

        String uri = thumbnailDownloadUseCase.execute(id);

        return ResponseEntity.status(HttpStatus.SEE_OTHER).location(URI.create(uri)).build();
    }

    private User assertAuthenticatedUser() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (!authentication.isAuthenticated()) {
//            throw new UnauthorizedUserException("Usuário não autenticado");
//        }
//        return (User) authentication.getPrincipal();
        return User.builder()
                .id(1L)
                .email("admin@fiap.com")
                .username("admin")
                .build();
    }

}
