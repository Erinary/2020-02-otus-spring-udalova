package ru.otus.erinary.hw03.quiz.service.localization;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.erinary.hw03.quiz.settings.AppSettings;

import java.util.Locale;

/**
 * Сервис для локализации сообщений
 */
@Service
public class LocalizationServiceImpl implements LocalizationService {

    private final MessageSource messageSource;
    private final Locale currentLocale;

    public LocalizationServiceImpl(final MessageSource messageSource, final AppSettings settings) {
        this.messageSource = messageSource;
        this.currentLocale = settings.getLocaleCode();
    }

    public String localizeMessage(final String code) {
        return messageSource.getMessage(code, null, currentLocale);
    }

    public String localizeMessageWithParams(final String code, final String[] params) {
        return messageSource.getMessage(code, params, currentLocale);
    }
}
