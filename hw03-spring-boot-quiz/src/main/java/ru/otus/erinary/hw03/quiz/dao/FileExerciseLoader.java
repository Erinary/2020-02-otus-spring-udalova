package ru.otus.erinary.hw03.quiz.dao;

import lombok.SneakyThrows;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import ru.otus.erinary.hw03.quiz.model.Exercise;
import ru.otus.erinary.hw03.quiz.settings.ApplicationSettings;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Реализация {@link ExerciseLoader} для загрузки из .csv файла
 */
@Component
public class FileExerciseLoader implements ExerciseLoader {

    private final String fileName;

    public FileExerciseLoader(final ApplicationSettings settings) {
        this.fileName = selectFileName(settings.getFileName(), settings.getLocaleCode().getLanguage());
    }

    @Override
    @SneakyThrows
    public List<Exercise> getExercises() {
        Reader reader = new FileReader(getFile());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withEscape('\\')
                .withFirstRecordAsHeader()
                .withHeader(FileHeaders.class)
                .parse(reader);

        List<Exercise> exercises = new ArrayList<>();
        for (CSVRecord record : records) {
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

    private String selectFileName(final String fileBaseName, final String localeCode) {
        String base = fileBaseName.substring(0, fileBaseName.lastIndexOf("."));
        String extension = fileBaseName.substring(fileBaseName.lastIndexOf(".") + 1);
        return String.format("%s-%s.%s", base, localeCode, extension);
    }

    private File getFile() {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new ExerciseLoaderException("File not found: " + fileName);
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
