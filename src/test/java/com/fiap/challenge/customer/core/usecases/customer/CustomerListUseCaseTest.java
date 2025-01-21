package com.fiap.challenge.user.core.usecases.user;

import com.fiap.challenge.user.core.domain.User;
import com.fiap.challenge.user.core.gateways.UserGateway;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserListUseCaseTest {

    private final UserGateway userGateway = mock(UserGateway.class);

    private final UserListUseCase userListUseCase = new UserListUseCase(userGateway);

    @Test
    void shouldListAll() {

        List<User> expected = List.of(new User(), new User());

        when(userGateway.findAll()).thenReturn(expected);

        List<User> actual = userListUseCase.execute(null);

        verify(userGateway).findAll();
        verify(userGateway, never()).findByDocument(any());

        assertEquals(expected, actual);
    }

    @Test
    void shouldListByDocument() {

        String document = "document";
        List<User> expected = List.of(new User(), new User());

        when(userGateway.findByDocument(document)).thenReturn(expected);

        List<User> actual = userListUseCase.execute(document);

        verify(userGateway).findByDocument(any());
        verify(userGateway, never()).findAll();

        assertEquals(expected, actual);
    }

}