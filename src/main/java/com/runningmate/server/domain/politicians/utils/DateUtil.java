package com.runningmate.server.domain.politicians.utils;

import com.runningmate.server.domain.politicians.exception.ParsingFailedException;
import com.runningmate.server.global.common.response.status.BaseExceptionResponseStatus;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Slf4j
public class DateUtil {

    public static LocalDate convertDateType(String date) {

        DateTimeFormatter formatterYYYY_MM_DD = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatterYYYYMMDD  = DateTimeFormatter.ofPattern("yyyyMMdd");

        try {
            if(date.matches("\\d{4}-\\d{2}-\\d{2}")){
                return LocalDate.parse(date, formatterYYYY_MM_DD);
            }
            if(date.matches("\\d{8}")) {
                return LocalDate.parse(date, formatterYYYYMMDD);
            }
            return null; // 아무런 포맷에 해당 안 되는 경우 (없어도 됨)
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public static boolean compareDate(LocalDate date1, LocalDate date2){
        return date1.isEqual(date2);
    }
}
