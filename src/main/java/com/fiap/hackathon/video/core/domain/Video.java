package com.fiap.hackathon.video.core.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Video {

    private Long id;
    private String name;
    private String contentType;
    private Long size;
    private VideoStatus status;
    private LocalDateTime createdAt;

}
