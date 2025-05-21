package com.runningmate.server.domain.auth.exception;

import com.runningmate.server.global.common.response.status.ResponseStatus;
import lombok.Getter;

@Getter
public class LoginFailedException extends RuntimeException {

    private final ResponseStatus exceptionStatus;

    public LoginFailedException(ResponseStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
        this.exceptionStatus = exceptionStatus;
    }
}
