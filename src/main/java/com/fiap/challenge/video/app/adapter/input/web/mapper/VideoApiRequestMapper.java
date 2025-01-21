package com.fiap.challenge.video.app.adapter.input.web.mapper;

import com.fiap.challenge.video.app.adapter.input.web.dto.VideoApiRequest;
import com.fiap.challenge.video.core.domain.Video;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VideoApiRequestMapper {

    Video toVideo(VideoApiRequest videoRequest);

}
