package com.fiap.hackathon.video.app.adapter.input.queue.video.dto;

import com.fiap.hackathon.video.core.domain.VideoStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VideoStatusChangedEvent {

    private Long id;
    private VideoStatus status;

}
