package com.fiap.hackathon.video.core.usecase;

import com.fiap.hackathon.video.app.adapter.output.persistence.gateway.UserGatewayImpl;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class SendMailUseCaseTest {

	private UserGatewayImpl userGatewayImpl;
	private SendMailUseCase sendMailUseCase;

	@BeforeEach
	void setUp() {
		userGatewayImpl = mock(UserGatewayImpl.class);
		sendMailUseCase = new SendMailUseCase(userGatewayImpl);
	}

	@Test
	void execute_shouldSendMailWhenParametersAreValid() throws MessagingException {
		String email = "test@example.com";
		Long videoId = 1L;

		sendMailUseCase.execute(email, videoId);

		verify(userGatewayImpl, times(1)).sendMmail(email, videoId);
	}

}