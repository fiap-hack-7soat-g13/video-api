package com.fiap.challenge.user.core.usecases.user;

import com.fiap.challenge.user.core.common.validator.UserCreateValidator;
import com.fiap.challenge.user.core.domain.User;
import com.fiap.challenge.user.core.gateways.UserGateway;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class UserCreateUseCaseTest {

    private final UserGateway userGateway = mock(UserGateway.class);

    private final UserCreateValidator userCreateValidator = mock(UserCreateValidator.class);

    private final UserCreateUseCase userCreateUseCase = new UserCreateUseCase(userGateway, userCreateValidator);

    @Test
    void shouldSave() {

        User user = new User();

        userCreateUseCase.execute(user);

        verify(userCreateValidator).validate(user);
        verify(userGateway).save(user);
    }

}