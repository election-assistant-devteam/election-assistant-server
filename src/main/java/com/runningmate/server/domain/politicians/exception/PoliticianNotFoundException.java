package com.runningmate.server.domain.politicians.exception;

import com.runningmate.server.global.common.response.status.ResponseStatus;
import lombok.Getter;

@Getter
public class PoliticianNotFoundException extends RuntimeException {

    private final ResponseStatus exceptionStatus;

    public PoliticianNotFoundException(ResponseStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
        this.exceptionStatus = exceptionStatus;
    }
}
