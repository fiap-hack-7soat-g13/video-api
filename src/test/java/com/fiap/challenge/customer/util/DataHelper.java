package com.fiap.challenge.user.util;

import com.fiap.challenge.user.app.adapter.input.web.dto.UserRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DataHelper {

    public static UserRequest createUserRequest() {
        UserRequest userRequest = new UserRequest();
        userRequest.setName("Nome 1");
        userRequest.setEmail("teste@teste.com.br");
        userRequest.setDocument("01234567890");
        userRequest.setPassword("123456");
        return userRequest;
    }

}
