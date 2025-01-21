package com.fiap.challenge.user.core.usecases.user;

import com.fiap.challenge.user.core.common.exception.EntityNotFoundException;
import com.fiap.challenge.user.core.domain.User;
import com.fiap.challenge.user.core.gateways.UserGateway;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserGetUseCaseTest {

    private final UserGateway userGateway = mock(UserGateway.class);

    private final UserGetUseCase userGetUseCase = new UserGetUseCase(userGateway);

    @Test
    void shouldGet() {

        UUID id = UUID.randomUUID();

        User expected = new User();

        when(userGateway.findById(id)).thenReturn(expected);

        User actual = userGetUseCase.execute(id);

        verify(userGateway).findById(id);

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowEntityNotFoundException() {

        UUID id = UUID.randomUUID();

        when(userGateway.findById(id)).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> userGetUseCase.execute(id));

        verify(userGateway).findById(id);
    }

}