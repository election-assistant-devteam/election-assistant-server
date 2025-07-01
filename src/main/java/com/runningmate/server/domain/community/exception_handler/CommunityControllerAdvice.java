package com.runningmate.server.domain.community.exception_handler;

import com.runningmate.server.domain.community.exception.AlreadyLikedException;
import com.runningmate.server.global.common.response.BaseErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class CommunityControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AlreadyLikedException.class)
    public BaseErrorResponse handle_AlreadyLikedException(AlreadyLikedException e){
        log.info("[handle_AlreadyLikedException]");
        return new BaseErrorResponse(e.getExceptionStatus());
    }
}
