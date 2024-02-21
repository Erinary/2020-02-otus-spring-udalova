package ru.otus.erinary.hw02.quiz.dao;

import lombok.SneakyThrows;
import org.apache.commons.csv.CSVFormat;
import ru.otus.erinary.hw02.quiz.model.Exercise;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Realization of {@link ExerciseLoader} for uploading from .csv file.
 */
public class FileExerciseLoader implements ExerciseLoader {

    private final String localeCode;
    private final String fileName;

    /**
     * Creates a new {@link FileExerciseLoader} instance.
     *
     * @param localeCode   current locale code
     * @param fileBaseName basic file name
     */
    public FileExerciseLoader(final String localeCode, final String fileBaseName) {
        this.localeCode = localeCode;
        this.fileName = selectFileName(fileBaseName);
    }

    @Override
    @SneakyThrows
    public List<Exercise> getExercises() {
        Reader reader = new FileReader(getFile());
        var records = CSVFormat.Builder.create().setEscape('\\')
                .setSkipHeaderRecord(true)
                .setHeader(FileHeaders.class)
                .build()
                .parse(reader);

        List<Exercise> exercises = new ArrayList<>();
        for (var record : records) {
            try {
                exercises.add(new Exercise(
                        record.get(FileHeaders.QUESTION),
                        getResponses(record.get(FileHeaders.RESPONSES)),
                        record.get(FileHeaders.ANSWER).trim()));
            } catch (Exception e) {
                throw new ExerciseLoaderException("Failed to read record from scv file", e);
            }
        }
        return exercises;
    }

    private String selectFileName(final String fileBaseName) {
        String base = fileBaseName.substring(0, fileBaseName.lastIndexOf("."));
        String extension = fileBaseName.substring(fileBaseName.lastIndexOf(".") + 1);
        return String.format("%s-%s.%s", base, localeCode, extension);
    }

    private File getFile() {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new ExerciseLoaderException("File not found");
        } else {
            return new File(resource.getFile());
        }
    }

    private List<String> getResponses(final String responses) {
        return Arrays.asList(responses.split(";"));
    }

    private enum FileHeaders {
        QUESTION, RESPONSES, ANSWER
    }
}
