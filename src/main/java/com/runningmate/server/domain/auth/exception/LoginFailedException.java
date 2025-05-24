package com.runningmate.server.domain.auth.exception;

import com.runningmate.server.global.common.exception.BadRequestException;
import com.runningmate.server.global.common.response.status.ResponseStatus;
import lombok.Getter;

@Getter
public class LoginFailedException extends BadRequestException {

    private final ResponseStatus exceptionStatus;

    public LoginFailedException(ResponseStatus exceptionStatus) {
        super(exceptionStatus);
        this.exceptionStatus = exceptionStatus;
    }
}
