package com.fiap.hackathon.video.app.adapter.input.web.video;

import com.fiap.hackathon.video.app.adapter.input.web.video.dto.VideoResponse;
import com.fiap.hackathon.video.app.adapter.input.web.video.mapper.VideoResponseMapper;
import com.fiap.hackathon.video.core.domain.Video;
import com.fiap.hackathon.video.core.usecase.VideoCreateUseCase;
import com.fiap.hackathon.video.core.usecase.VideoGetUseCase;
import com.fiap.hackathon.video.core.usecase.VideoListUseCase;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@AllArgsConstructor
public class VideoController {

    private final VideoCreateUseCase videoCreateUseCase;
    private final VideoGetUseCase videoGetUseCase;
    private final VideoListUseCase videoListUseCase;
    private final VideoResponseMapper videoResponseMapper;

    @PostMapping
    public VideoResponse create(@RequestParam MultipartFile file) {
        Video video = videoCreateUseCase.execute(file);
        return videoResponseMapper.toVideoResponse(video);
    }

    @GetMapping("/{id}")
    public VideoResponse get(@PathVariable Long id) {
        Video video = videoGetUseCase.execute(id);
        return videoResponseMapper.toVideoResponse(video);
    }

    @GetMapping
    public List<VideoResponse> list() {
        List<Video> videos = videoListUseCase.execute();
        return videos.stream().map(videoResponseMapper::toVideoResponse).toList();
    }

}
