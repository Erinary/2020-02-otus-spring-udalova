package ru.otus.erinary.hw03.quiz.settings;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Class that stores application's properties.
 */
@Component
@ConfigurationProperties(prefix = "application")
public class AppSettings {

    private Locale localeCode;
    private String fileName;

    public Locale getLocaleCode() {
        return localeCode;
    }

    public void setLocaleCode(final String localeCode) {
        this.localeCode = Locale.forLanguageTag(localeCode);
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(final String fileName) {
        this.fileName = fileName;
    }

}
