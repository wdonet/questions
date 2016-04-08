package com.nearsoft.questions.controller.converters;


import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ZonedDateTimeToString implements Formatter<ZonedDateTime> {

    @Override
    public ZonedDateTime parse(String s, Locale locale) throws ParseException {
        return null;
    }

    @Override
    public String print(ZonedDateTime zonedDateTime, Locale locale) {
        return zonedDateTime.format(DateTimeFormatter.ofPattern("d L YY - HH:mm"));
    }


}
