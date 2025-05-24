package com.runningmate.server.domain.user.exception_handler;

import com.runningmate.server.domain.user.exception.SameUserExistsException;
import com.runningmate.server.global.common.response.BaseErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.runningmate.server.global.common.response.status.BaseExceptionResponseStatus.SAME_USER_INFO_EXISTS;

@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class UserControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SameUserExistsException.class)
    public BaseErrorResponse handle_SameUserExistsException(SameUserExistsException e){
        log.info("[handle_SameUserExistsException]");
        return new BaseErrorResponse(SAME_USER_INFO_EXISTS);
    }
}
