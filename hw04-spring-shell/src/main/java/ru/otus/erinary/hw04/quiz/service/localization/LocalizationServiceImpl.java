package ru.otus.erinary.hw04.quiz.service.localization;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.erinary.hw04.quiz.settings.AppSettings;

import java.util.Locale;

/**
 * Realization of {@link LocalizationService}.
 */
@Service
public class LocalizationServiceImpl implements LocalizationService {

    private final MessageSource messageSource;
    private final Locale currentLocale;

    /**
     * Create a new {@link LocalizationServiceImpl} instance.
     *
     * @param messageSource {@link MessageSource}
     * @param settings      {@link AppSettings}
     */
    public LocalizationServiceImpl(final MessageSource messageSource, final AppSettings settings) {
        this.messageSource = messageSource;
        this.currentLocale = settings.getLocaleCode();
    }

    @Override
    public String localizeMessage(final String code) {
        return messageSource.getMessage(code, null, currentLocale);
    }

    @Override
    public String localizeMessageWithParams(final String code, final String[] params) {
        return messageSource.getMessage(code, params, currentLocale);
    }
}
