package com.fiap.hackathon.video.core.usecase;

import com.fiap.hackathon.video.app.adapter.output.persistence.gateway.UserGatewayImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SendMailUseCase {

    private final UserGatewayImpl userGatewayImpl;

    public void execute(String email, Long videoId) {
        userGatewayImpl.sendMmail(email, videoId);
    }

}
