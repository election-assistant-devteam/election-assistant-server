package com.runningmate.server.domain.politicians.exception;

import com.runningmate.server.global.common.exception.CustomException;
import com.runningmate.server.global.common.response.status.ResponseStatus;
import lombok.Getter;

@Getter
public class ExternalApiNotAvailableException extends CustomException {
    public ExternalApiNotAvailableException(ResponseStatus exceptionStatus){
        super(exceptionStatus);
    }
}
