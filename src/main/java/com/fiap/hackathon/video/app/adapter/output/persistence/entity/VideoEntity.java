package com.fiap.hackathon.video.app.adapter.output.persistence.entity;

import com.fiap.hackathon.video.core.domain.VideoStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "video")
public class VideoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String contentType;

    private Long size;

    @Enumerated(EnumType.STRING)
    private VideoStatus status;

    private LocalDateTime createdAt;

    private Long createdBy;

    private String createdByEmail;

}
