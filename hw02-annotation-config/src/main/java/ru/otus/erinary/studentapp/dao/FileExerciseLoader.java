package ru.otus.erinary.studentapp.dao;

import lombok.SneakyThrows;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.erinary.studentapp.model.Exercise;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class FileExerciseLoader implements ExerciseLoader {

    private String fileName;

    public FileExerciseLoader(@Value("${exercise.file.name}") String fileName) {
        this.fileName = fileName;
    }

    @Override
    @SneakyThrows
    public List<Exercise> getExercises() {
        Reader reader = new FileReader(getFile());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .withHeader(FileHeaders.class)
                .parse(reader);
        return StreamSupport.stream(records.spliterator(), false)
                .map(record -> new Exercise(record.get(FileHeaders.QUESTION),
                        getResponses(record.get(FileHeaders.RESPONSES)), record.get(FileHeaders.ANSWER)))
                .collect(Collectors.toList());
    }

    private File getFile() {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("File not found");
        } else {
            return new File(resource.getFile());
        }
    }

    private List<String> getResponses(String responses) {
        return Arrays.asList(responses.split(";"));
    }

    private enum FileHeaders {
        QUESTION, RESPONSES, ANSWER
    }
}
