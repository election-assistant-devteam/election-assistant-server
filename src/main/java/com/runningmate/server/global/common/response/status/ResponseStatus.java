package com.runningmate.server.global.common.response.status;

public interface ResponseStatus {
    boolean getSuccess();

    int getCode();

    String getMessage();

}