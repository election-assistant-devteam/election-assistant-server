package com.runningmate.server.domain.politicians.exception_handler;

import com.runningmate.server.domain.politicians.exception.ElectionNotFoundException;
import com.runningmate.server.domain.politicians.exception.ParsingFailedException;
import com.runningmate.server.domain.user.exception.SameUserExistsException;
import com.runningmate.server.global.common.response.BaseErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.runningmate.server.global.common.response.status.BaseExceptionResponseStatus.*;

@Slf4j
@RestControllerAdvice
public class PoliticianControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ElectionNotFoundException.class)
    public BaseErrorResponse handle_ElectionNotFoundException(ElectionNotFoundException e){
        log.info("[handle_ElectionNotFoundException]");
        return new BaseErrorResponse(FIND_ELECTION_FAILED);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ParsingFailedException.class)
    public BaseErrorResponse handle_ParsingFailedException(ParsingFailedException e){
        log.info("[handle_ParsingFailedException]");
        return new BaseErrorResponse(PARSING_FAILED);
    }
}
