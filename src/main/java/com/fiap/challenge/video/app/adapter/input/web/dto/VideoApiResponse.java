package com.fiap.challenge.video.app.adapter.input.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class VideoApiResponse {

    private String name;
    private LocalDateTime createdAt;

}
