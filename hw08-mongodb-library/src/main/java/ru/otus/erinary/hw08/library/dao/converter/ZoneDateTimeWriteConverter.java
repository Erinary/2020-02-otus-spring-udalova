package ru.otus.erinary.hw08.library.dao.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Custom converter for ZonedDateTime type for writing to MongoDB.
 */
@WritingConverter
public class ZoneDateTimeWriteConverter implements Converter<ZonedDateTime, Date> {

    @Override
    public Date convert(final ZonedDateTime zonedDateTime) {
        return Date.from(zonedDateTime.toInstant());
    }
}
