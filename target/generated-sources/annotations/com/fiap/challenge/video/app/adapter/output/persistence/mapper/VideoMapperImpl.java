package com.fiap.challenge.video.app.adapter.output.persistence.mapper;

import com.fiap.challenge.video.app.adapter.output.persistence.entity.VideoEntity;
import com.fiap.challenge.video.core.domain.Video;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-21T00:03:21-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Azul Systems, Inc.)"
)
@Component
public class VideoMapperImpl implements VideoMapper {

    @Override
    public VideoEntity toVideoEntity(Video video) {
        if ( video == null ) {
            return null;
        }

        VideoEntity.VideoEntityBuilder videoEntity = VideoEntity.builder();

        videoEntity.id( video.getId() );
        videoEntity.name( video.getName() );

        return videoEntity.build();
    }

    @Override
    public Video toVideo(VideoEntity videoEntity) {
        if ( videoEntity == null ) {
            return null;
        }

        Video video = new Video();

        video.setId( videoEntity.getId() );
        video.setName( videoEntity.getName() );

        return video;
    }

    @Override
    public List<Video> toVideo(List<VideoEntity> videoEntities) {
        if ( videoEntities == null ) {
            return null;
        }

        List<Video> list = new ArrayList<Video>( videoEntities.size() );
        for ( VideoEntity videoEntity : videoEntities ) {
            list.add( toVideo( videoEntity ) );
        }

        return list;
    }
}
