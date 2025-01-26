package com.fiap.hackathon.video.app.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpConfig {

    private final String videoReceivedQueue;
    private final String videoStatusChangedQueue;

    public AmqpConfig(@Value("${application.queue.videoReceived.name}") String videoReceivedQueue,
                      @Value("${application.queue.videoStatusChanged.name}") String videoStatusChangedQueue) {
        this.videoReceivedQueue = videoReceivedQueue;
        this.videoStatusChangedQueue = videoStatusChangedQueue;
    }

    @Bean
    public MessageConverter converter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public Queue videoReceivedQueue() {
        return new Queue(videoReceivedQueue);
    }

    @Bean
    public Queue videoStatusChangedQueue() {
        return new Queue(videoStatusChangedQueue);
    }

}
