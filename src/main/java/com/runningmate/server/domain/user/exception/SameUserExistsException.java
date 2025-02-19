package com.runningmate.server.domain.user.exception;

import com.runningmate.server.global.common.response.status.ResponseStatus;
import lombok.Getter;

@Getter
public class SameUserExistsException extends RuntimeException {

    private final ResponseStatus exceptionStatus;

    public SameUserExistsException(ResponseStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
        this.exceptionStatus = exceptionStatus;
    }
}
