package com.runningmate.server.domain.auth.exception_handler;

import com.runningmate.server.domain.auth.exception.LoginFailedException;
import com.runningmate.server.domain.user.exception.SameUserExistsException;
import com.runningmate.server.global.common.response.BaseErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.runningmate.server.global.common.response.status.BaseExceptionResponseStatus.LOGIN_FAILED;
import static com.runningmate.server.global.common.response.status.BaseExceptionResponseStatus.SAME_USER_INFO_EXISTS;

@Slf4j
@RestControllerAdvice
public class AuthControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(LoginFailedException.class)
    public BaseErrorResponse handle_LoginFailedException(LoginFailedException e){
        log.info("[handle_LoginFailedException]");
        return new BaseErrorResponse(LOGIN_FAILED);
    }
}
