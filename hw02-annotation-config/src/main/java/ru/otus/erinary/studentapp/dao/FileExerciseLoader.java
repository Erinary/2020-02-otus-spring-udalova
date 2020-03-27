package ru.otus.erinary.studentapp.dao;

import lombok.SneakyThrows;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import ru.otus.erinary.studentapp.model.Exercise;

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
public class FileExerciseLoader implements ExerciseLoader {

    private final String localeCode;
    private final String fileName;

    public FileExerciseLoader(final String localeCode, final String fileBaseName) {
        this.localeCode = localeCode;
        this.fileName = selectFileName(fileBaseName);
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
