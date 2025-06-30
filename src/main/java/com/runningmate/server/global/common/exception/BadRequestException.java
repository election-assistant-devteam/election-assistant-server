package com.runningmate.server.global.common.exception;

import com.runningmate.server.global.common.response.status.ResponseStatus;
import lombok.Getter;

@Getter
public class BadRequestException extends CustomException {
    public BadRequestException(ResponseStatus exceptionStatus) {
        super(exceptionStatus);
    }
}
