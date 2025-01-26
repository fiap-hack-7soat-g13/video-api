package com.fiap.hackathon.video.app.adapter.output.persistence.mapper;

import com.fiap.hackathon.video.app.adapter.output.persistence.entity.VideoEntity;
import com.fiap.hackathon.video.core.domain.Video;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VideoEntityMapper {

    VideoEntity toVideoEntity(Video video);

    Video toVideo(VideoEntity videoEntity);

}
