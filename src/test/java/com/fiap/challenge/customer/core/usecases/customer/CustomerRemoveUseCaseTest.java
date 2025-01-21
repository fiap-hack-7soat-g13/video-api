package com.fiap.challenge.user.core.usecases.user;

import com.fiap.challenge.user.core.gateways.UserGateway;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class UserRemoveUseCaseTest {

    private final UserGateway userGateway = mock(UserGateway.class);

    private final UserRemoveUseCase userRemoveUseCase = new UserRemoveUseCase(userGateway);

    @Test
    void shouldRemove() {

        UUID id = UUID.randomUUID();

        userRemoveUseCase.execute(id);

        verify(userGateway).removeById(id);
    }

}