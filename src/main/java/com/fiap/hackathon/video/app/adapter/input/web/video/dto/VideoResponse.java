package com.fiap.hackathon.video.app.adapter.input.web.video.dto;

import com.fiap.hackathon.video.core.domain.VideoStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class VideoResponse {

    private Long id;
    private String name;
    private String contentType;
    private Long size;
    private VideoStatus status;
    private LocalDateTime createdAt;
    private Long createdBy;
    private String createdByEmail;

}
