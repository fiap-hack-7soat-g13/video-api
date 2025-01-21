package com.fiap.challenge.video.app.adapter.output.persistence.mapper;

import com.fiap.challenge.video.app.adapter.output.persistence.entity.VideoEntity;
import com.fiap.challenge.video.core.domain.Video;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VideoMapper {

    VideoEntity toVideoEntity(Video video);

    Video toVideo(VideoEntity videoEntity);

    List<Video> toVideo(List<VideoEntity> videoEntities);

}
