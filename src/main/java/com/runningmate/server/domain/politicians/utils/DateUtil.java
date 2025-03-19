package com.runningmate.server.domain.politicians.utils;

import com.runningmate.server.domain.politicians.exception.ParsingFailedException;
import com.runningmate.server.global.common.response.status.BaseExceptionResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class DateUtil {

    public static Date convertDateType(String date) {

        SimpleDateFormat formatterYYYYMMDD  = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat formatterYYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");

        Date convertedDate = null;
        try {
            if(date.matches("\\d{4}-\\d{2}-\\d{2}")){
                convertedDate = formatterYYYY_MM_DD.parse(date);
            }else if(date.matches("\\d{8}")) {
                convertedDate = formatterYYYYMMDD.parse(date);
            }else{
                return null;
            }
        } catch (ParseException e) {
            log.info("fail reason {}", date);
            throw new ParsingFailedException(BaseExceptionResponseStatus.PARSING_FAILED);
        }
        return convertedDate;
    }

    public static boolean compareDate(Date date1, Date date2){
        return date1.compareTo(date2) == 0;
    }
}
