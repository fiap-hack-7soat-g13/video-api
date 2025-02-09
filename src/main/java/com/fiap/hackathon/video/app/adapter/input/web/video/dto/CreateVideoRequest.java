package com.fiap.hackathon.video.app.adapter.input.web.video.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateVideoRequest {

    private UUID uploadId;
    private String fileName;

}
