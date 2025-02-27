package com.runningmate.server.domain.politicians.exception;

import com.runningmate.server.global.common.response.status.ResponseStatus;
import lombok.Getter;

@Getter
public class ParsingFailedException extends RuntimeException{

    private final ResponseStatus exceptionStatus;

    public ParsingFailedException(ResponseStatus exceptionStatus){
        super(exceptionStatus.getMessage());
        this.exceptionStatus = exceptionStatus;
    }
}
