package ru.otus.erinary.hw01.quiz.service.interaction.scanner;

import java.util.Scanner;

/**
 * Фабрика для {@link Scanner}.
 */
public interface ScannerFactory {

    /**
     * Предоставляет экземпляр {@link Scanner}.
     *
     * @return {@link Scanner}
     */
    Scanner createScanner();

}
