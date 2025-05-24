package com.runningmate.server.domain.user.exception;

import com.runningmate.server.global.common.exception.BadRequestException;
import com.runningmate.server.global.common.response.status.ResponseStatus;
import lombok.Getter;

@Getter
public class SameUserExistsException extends BadRequestException {

    private final ResponseStatus exceptionStatus;

    public SameUserExistsException(ResponseStatus exceptionStatus) {
        super(exceptionStatus);
        this.exceptionStatus = exceptionStatus;
    }
}
