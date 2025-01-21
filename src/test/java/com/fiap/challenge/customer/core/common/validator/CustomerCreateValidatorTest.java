package com.fiap.challenge.user.core.common.validator;

import com.fiap.challenge.user.core.common.exception.InvalidDataException;
import com.fiap.challenge.user.core.domain.User;
import com.fiap.challenge.user.core.gateways.UserGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.*;

class UserCreateValidatorTest {

    public static Stream<Arguments> shouldValidatePassword() {
        return Stream.of(
                arguments(null, "É obrigatório informar a senha"),
                arguments("aA@1", "A senha deve conter ao menos 8 caracteres"),
                arguments("aaaaaaaa", "A senha deve conter letras maiúsculas, minúsculas, números e caracteres especiais")
        );
    }

    private final UserGateway userGateway = mock(UserGateway.class);

    private final UserCreateValidator userCreateValidator = new UserCreateValidator(userGateway);

    @Test
    void shouldBeValid() {

        User user = createValidUser();

        when(userGateway.findByDocument(user.getDocument())).thenReturn(Collections.emptyList());
        when(userGateway.findByEmail(user.getEmail())).thenReturn(Collections.emptyList());

        assertDoesNotThrow(() -> userCreateValidator.validate(user));

        verify(userGateway).findByDocument(user.getDocument());
        verify(userGateway).findByEmail(user.getEmail());
    }

    @Test
    void shouldValidateEmptyName() {

        User user = createValidUser();

        user.setName(null);

        when(userGateway.findByDocument(user.getDocument())).thenReturn(Collections.emptyList());
        when(userGateway.findByEmail(user.getEmail())).thenReturn(Collections.emptyList());

        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> userCreateValidator.validate(user));

        assertEquals(List.of("É obrigatório informar o nome"), exception.getMessages());

        verify(userGateway).findByDocument(user.getDocument());
        verify(userGateway).findByEmail(user.getEmail());
    }

    @Test
    void shouldValidateEmptyEmail() {

        User user = createValidUser();

        user.setEmail(null);

        when(userGateway.findByDocument(user.getDocument())).thenReturn(Collections.emptyList());
        when(userGateway.findByEmail(user.getEmail())).thenReturn(Collections.emptyList());

        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> userCreateValidator.validate(user));

        assertEquals(List.of("É obrigatório informar o e-mail"), exception.getMessages());

        verify(userGateway).findByDocument(user.getDocument());
        verify(userGateway, never()).findByEmail(user.getEmail());
    }

    @Test
    void shouldValidateInvalidEmail() {

        User user = createValidUser();

        user.setEmail("email");

        when(userGateway.findByDocument(user.getDocument())).thenReturn(Collections.emptyList());
        when(userGateway.findByEmail(user.getEmail())).thenReturn(Collections.emptyList());

        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> userCreateValidator.validate(user));

        assertEquals(List.of("O e-mail informado é inválido"), exception.getMessages());

        verify(userGateway).findByDocument(user.getDocument());
        verify(userGateway).findByEmail(user.getEmail());
    }

    @Test
    void shouldValidateEmailAlreadyExists() {

        User user = createValidUser();

        when(userGateway.findByDocument(user.getDocument())).thenReturn(Collections.emptyList());
        when(userGateway.findByEmail(user.getEmail())).thenReturn(List.of(new User()));

        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> userCreateValidator.validate(user));

        assertEquals(List.of(String.format("Já existe um cliente com o e-mail '%s'", user.getEmail())), exception.getMessages());

        verify(userGateway).findByDocument(user.getDocument());
        verify(userGateway).findByEmail(user.getEmail());
    }

    @Test
    void shouldValidateEmptyDocument() {

        User user = createValidUser();

        user.setDocument(null);

        when(userGateway.findByDocument(user.getDocument())).thenReturn(Collections.emptyList());
        when(userGateway.findByEmail(user.getEmail())).thenReturn(Collections.emptyList());

        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> userCreateValidator.validate(user));

        assertEquals(List.of("É obrigatório informar o documento"), exception.getMessages());

        verify(userGateway, never()).findByDocument(user.getDocument());
        verify(userGateway).findByEmail(user.getEmail());
    }

    @Test
    void shouldValidateInvalidDocument() {

        User user = createValidCUser();

        user.setDocument("document");

        when(userGateway.findByDocument(user.getDocument())).thenReturn(Collections.emptyList());
        when(userGateway.findByEmail(user.getEmail())).thenReturn(Collections.emptyList());

        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> userCreateValidator.validate(user));

        assertEquals(List.of("O documento informado é inválido"), exception.getMessages());

        verify(userGateway).findByDocument(user.getDocument());
        verify(userGateway).findByEmail(user.getEmail());
    }

    @Test
    void shouldValidateDocumentAlreadyExists() {

        User user = createValidUser();

        when(userGateway.findByDocument(user.getDocument())).thenReturn(List.of(new User()));
        when(userGateway.findByEmail(user.getEmail())).thenReturn(Collections.emptyList());

        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> userCreateValidator.validate(user));

        assertEquals(List.of(String.format("Já existe um cliente com o documento '%s'", user.getDocument())), exception.getMessages());

        verify(userGateway).findByDocument(user.getDocument());
        verify(userGateway).findByEmail(user.getEmail());
    }

    @ParameterizedTest
    @MethodSource
    void shouldValidatePassword(String password, String message) {

        User user = createValidUser();

        user.setPassword(password);

        when(userGateway.findByDocument(user.getDocument())).thenReturn(Collections.emptyList());
        when(userGateway.findByEmail(user.getEmail())).thenReturn(Collections.emptyList());

        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> userCreateValidator.validate(user));

        assertEquals(List.of(message), exception.getMessages());

        verify(userGateway).findByDocument(user.getDocument());
        verify(userGateway).findByEmail(user.getEmail());
    }

    private User createValidUser() {

        User user = new User();

        user.setName("Bill Gates");
        user.setEmail("bill.gates@microsoft.com");
        user.setDocument("44867508020");
        user.setPassword("abc1@XYZ");

        return user;
    }

}