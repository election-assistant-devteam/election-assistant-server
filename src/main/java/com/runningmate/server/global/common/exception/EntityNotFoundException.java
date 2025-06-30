package com.runningmate.server.global.common.exception;

import com.runningmate.server.global.common.response.status.ResponseStatus;
import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException {

    private final ResponseStatus exceptionStatus;

    public EntityNotFoundException(ResponseStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
        this.exceptionStatus = exceptionStatus;
    }
}
