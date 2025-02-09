package com.fiap.hackathon.video.core.usecase;

import com.fiap.hackathon.video.app.adapter.output.persistence.gateway.UserGatewayImpl;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SendMailUseCase {

    private final UserGatewayImpl userGatewayImpl;

    public void execute(String email, Long videoId) throws MessagingException {
        userGatewayImpl.sendMmail(email, videoId);
    }

}
