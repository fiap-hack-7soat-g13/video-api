package com.fiap.challenge.video.app.adapter.input.web.mapper;

import com.fiap.challenge.video.app.adapter.input.web.dto.VideoApiResponse;
import com.fiap.challenge.video.core.domain.Video;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VideoApiResponseMapper {

    VideoApiResponse toVideoResponse(Video video);

    List<VideoApiResponse> toVideo(List<Video> videos);

}
