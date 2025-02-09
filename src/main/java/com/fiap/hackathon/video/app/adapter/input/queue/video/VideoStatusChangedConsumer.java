package com.fiap.hackathon.video.app.adapter.input.queue.video;

import com.fiap.hackathon.video.app.adapter.input.queue.video.dto.VideoStatusChangedEvent;
import com.fiap.hackathon.video.core.domain.Video;
import com.fiap.hackathon.video.core.domain.VideoStatus;
import com.fiap.hackathon.video.core.usecase.SendMailUseCase;
import com.fiap.hackathon.video.core.usecase.VideoGetUseCase;
import com.fiap.hackathon.video.core.usecase.VideoStatusUpdateUseCase;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class VideoStatusChangedConsumer {

    private final VideoStatusUpdateUseCase videoStatusUpdateUseCase;
    private final VideoGetUseCase videoGetUseCase;
    private final SendMailUseCase sendMailUseCase;

    @Transactional
    @RabbitListener(queues = "${application.queue.videoStatusChanged.name}")
    public void consume(VideoStatusChangedEvent event) {
        videoStatusUpdateUseCase.execute(event.getId(), event.getStatus());
        if (VideoStatus.FAILED == event.getStatus()) {
            Video video = videoGetUseCase.execute(event.getId());
            sendMailUseCase.execute(video.getCreatedByEmail(), event.getId());
        }
    }

}
