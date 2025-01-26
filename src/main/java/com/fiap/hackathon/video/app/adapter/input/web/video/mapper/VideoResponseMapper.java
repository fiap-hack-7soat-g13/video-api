package com.fiap.hackathon.video.app.adapter.input.web.video.mapper;

import com.fiap.hackathon.video.app.adapter.input.web.video.dto.VideoResponse;
import com.fiap.hackathon.video.core.domain.Video;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VideoResponseMapper {

    VideoResponse toVideoResponse(Video video);

}
