package ru.javawebinar.topjava.util;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;

final class StringToDateFormatter implements Converter<String, LocalDate> {

    public LocalDate convert(String source) {
        return LocalDate.parse(source);
    }
}
