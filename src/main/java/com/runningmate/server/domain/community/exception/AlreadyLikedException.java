package com.runningmate.server.domain.community.exception;

import com.runningmate.server.global.common.exception.CustomException;
import com.runningmate.server.global.common.response.status.ResponseStatus;

public class AlreadyLikedException extends CustomException {
    public AlreadyLikedException(ResponseStatus exceptionStatus) {
        super(exceptionStatus);
    }
}
