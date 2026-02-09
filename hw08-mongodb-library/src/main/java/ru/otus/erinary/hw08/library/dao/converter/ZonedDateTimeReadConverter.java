package ru.otus.erinary.hw08.library.dao.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Custom converter for ZonedDateTime type for reading from MongoDB.
 */
@ReadingConverter
public class ZonedDateTimeReadConverter implements Converter<Date, ZonedDateTime> {

    @Override
    public ZonedDateTime convert(final Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault());
    }

}
