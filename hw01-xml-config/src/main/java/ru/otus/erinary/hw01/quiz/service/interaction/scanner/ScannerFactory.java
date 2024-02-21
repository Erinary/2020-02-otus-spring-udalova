package ru.otus.erinary.hw01.quiz.service.interaction.scanner;

import java.util.Scanner;

/**
 * Factory for {@link Scanner}.
 */
public interface ScannerFactory {

    /**
     * Creates new instance of {@link Scanner}.
     *
     * @return {@link Scanner}
     */
    Scanner createScanner();

}
