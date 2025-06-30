package com.runningmate.server.global.common.exception;

import com.runningmate.server.global.common.response.status.ResponseStatus;
import lombok.Getter;

@Getter
public class EntityNotFoundException extends CustomException {
    public EntityNotFoundException(ResponseStatus exceptionStatus) {
        super(exceptionStatus);
    }
}
