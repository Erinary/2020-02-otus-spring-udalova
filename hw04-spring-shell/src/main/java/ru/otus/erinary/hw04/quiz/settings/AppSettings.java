package ru.otus.erinary.hw04.quiz.settings;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Хранилище настроек приложения
 */
@Component
@ConfigurationProperties(prefix = "application")
public class AppSettings {

    private Locale localeCode;
    private String fileName;

    public Locale getLocaleCode() {
        return localeCode;
    }

    public void setLocaleCode(String localeCode) {
        this.localeCode = Locale.forLanguageTag(localeCode);
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
