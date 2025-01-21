package com.fiap.challenge.video.app.adapter.input.web;

import com.fiap.challenge.video.app.adapter.input.web.dto.VideoApiRequest;
import com.fiap.challenge.video.app.adapter.input.web.dto.VideoApiResponse;
import com.fiap.challenge.video.app.adapter.input.web.mapper.VideoApiRequestMapper;
import com.fiap.challenge.video.app.adapter.input.web.mapper.VideoApiResponseMapper;
import com.fiap.challenge.video.core.domain.Video;
import com.fiap.challenge.video.core.usecases.video.VideoCreateUseCase;
import com.fiap.challenge.video.core.usecases.video.VideoGetUseCase;
import com.fiap.challenge.video.core.usecases.video.VideoListUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class VideoApiController {

    private final VideoCreateUseCase videoCreateUseCase;
    private final VideoGetUseCase videoGetUseCase;
    private final VideoListUseCase videoListUseCase;
    private final VideoApiRequestMapper videoRequestMapper;
    private final VideoApiResponseMapper videoResponseMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VideoApiResponse create(@RequestBody VideoApiRequest videoRequest) {
        Video video = videoRequestMapper.toVideo(videoRequest);
        Video videoSave = videoCreateUseCase.execute(video);
        return videoResponseMapper.toVideoResponse(videoSave);
    }

    @GetMapping("/{id}")
    public VideoApiResponse get(@PathVariable Long id) {
        Video video = videoGetUseCase.execute(id);
        return videoResponseMapper.toVideoResponse(video);
    }

    @GetMapping
    public List<VideoApiResponse> list(@RequestParam(required = false) String document) {
        List<Video> videoList = videoListUseCase.execute(document);
        return videoResponseMapper.toVideo(videoList);
    }

}
