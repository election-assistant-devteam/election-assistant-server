package com.runningmate.server.domain.politicians.utils;

import com.runningmate.server.domain.politicians.exception.ParsingFailedException;
import com.runningmate.server.global.common.response.status.BaseExceptionResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Component
public class DateUtil {

    public Date convertDateType(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        Date convertedDate = null;
        try {
            convertedDate = formatter.parse(date);
        } catch (ParseException e) {
            throw new ParsingFailedException(BaseExceptionResponseStatus.PARSING_FAILED);
        }
        return convertedDate;
    }
}
