package com.home.ans.holidays.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

@Component
@Slf4j
public class ResponseStorage {
    private static final String FILE_TIME_FORMAT = "yyyy_MM_dd_HH_mm_ss_SSS";
    private static final String PATH_SEPARATOR = "\\";

    @Value("${travel.directory}")
    private String DIRECTORY;

    @Value("${travel.filename}")
    private String FILENAME;

    @Value("${travel.extension}")
    private String EXTENSION;

    private Function<ResponseEntity, LocalDateTime> headerDateTypeConverter;

    public void logStatus(ResponseEntity response) {
        if (Objects.isNull(response.getBody())) {
            log.error("Something went wrong - response body is null!");
        } else {
            log.debug("Response returned with status: {}", response.getStatusCodeValue());
        }
    }

    public Path writeToFile(ResponseEntity response) throws IOException {
        Path filePath = Paths.get(getFilePath());

        Files.createDirectories(filePath.getParent());

        try (FileWriter fileWriter = new FileWriter(filePath.toFile(), true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            bufferedWriter
                    .append(String.format("RESPONSE FROM DATE: %s", headerDateTypeConverter.apply(response)))
                    .append(System.lineSeparator())
                    .append(String.format("HTTP STATUS: %d", response.getStatusCodeValue()))
                    .append(System.lineSeparator())
                    .append((String) response.getBody())
                    .append(System.lineSeparator());
            log.info("The response has been saved to location: {}", filePath.getParent());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }

    public void cleanLogs(int olderThanDaysAmount) throws IOException {
        Path dir = Paths.get(getFilePath()).getParent();
        try (Stream<Path> stream = Files.walk(dir, 1)) {
            stream.filter(file -> !Files.isDirectory(file))
                    .filter(file -> getFilesOlderThan(olderThanDaysAmount, file))
                    .forEach(this::deleteIfExists);
        }
    }

    private boolean deleteIfExists(Path file) {
        try {
            return Files.deleteIfExists(file);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean getFilesOlderThan(int olderThanDays, Path file) {
        FileTime fileCreationTime = null;
        try {
            fileCreationTime = (FileTime) Files.getAttribute(file, "creationTime");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileCreationTime.toInstant().isBefore(Instant.now().minus(olderThanDays, ChronoUnit.DAYS));
    }

    private String getFilePath() {
//        System.getenv().entrySet().forEach(entry -> log.info("key: {}, value: {}", entry.getKey(), entry.getValue()));
        return System.getenv("HOMEDRIVE")
                .concat(System.getenv("HOMEPATH"))
                .concat(PATH_SEPARATOR)
                .concat(DIRECTORY)
                .concat(PATH_SEPARATOR)
                .concat(FILENAME)
                .concat("_")
                .concat(LocalDateTime.now().format(DateTimeFormatter.ofPattern(FILE_TIME_FORMAT)))
                .concat(".")
                .concat(EXTENSION);
    }

    @Autowired
    @Qualifier("headerDateTypeConverter")
    public void setHeaderDateTypeConverter(Function<ResponseEntity, LocalDateTime> headerDateTypeConverter) {
        this.headerDateTypeConverter = headerDateTypeConverter;
    }
}
