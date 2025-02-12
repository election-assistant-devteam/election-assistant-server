package com.runningmate.server.domain.user.dto;

import lombok.Getter;

@Getter
public class CreateUserRequest {
    private String username;
    private String password;
    private String email;
    private String nickname;
}
