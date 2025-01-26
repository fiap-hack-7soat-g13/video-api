package com.fiap.hackathon.video.app.adapter.output.queue.rabbitmq;

import com.fiap.hackathon.video.app.adapter.output.queue.VideoReceivedDispatcher;
import com.fiap.hackathon.video.app.adapter.output.queue.VideoReceivedEvent;
import com.fiap.hackathon.video.core.domain.Video;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class VideoReceivedDispatcherImpl implements VideoReceivedDispatcher {

    private final RabbitTemplate rabbitTemplate;
    private final String videoReceivedQueue;

    public VideoReceivedDispatcherImpl(RabbitTemplate rabbitTemplate, @Value("${application.queue.videoReceived.name}") String videoReceivedQueue) {
        this.rabbitTemplate = rabbitTemplate;
        this.videoReceivedQueue = videoReceivedQueue;
    }

    @Override
    public void dispatch(Video video) {

        VideoReceivedEvent event = VideoReceivedEvent.builder()
                .id(video.getId())
                .build();

        rabbitTemplate.convertAndSend(videoReceivedQueue, event);
    }

}
