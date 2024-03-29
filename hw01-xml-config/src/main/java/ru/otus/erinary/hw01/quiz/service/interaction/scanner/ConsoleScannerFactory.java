package ru.otus.erinary.hw01.quiz.service.interaction.scanner;

import java.util.Scanner;

/**
 * {@link Scanner} for working with the console.
 */
public class ConsoleScannerFactory implements ScannerFactory {

    @Override
    public final Scanner createScanner() {
        return new Scanner(System.in);
    }

}
