package com.home.ans.holidays.component;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.function.Function;

import static com.home.ans.holidays.service.impl.ParserServiceImplTest.prepareResponseBodyJson;
import static org.testng.Assert.assertTrue;

public class ResponseStorageTest {

    @Mock
    private Function<ResponseEntity, LocalDateTime> headerDateTypeConverter;

    @InjectMocks
    private ResponseStorage responseStorage;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(responseStorage, "DIRECTORY", "\\MyApps\\Travel-Searcher");
        ReflectionTestUtils.setField(responseStorage, "FILENAME", "response_api");
        ReflectionTestUtils.setField(responseStorage, "EXTENSION", "log");
    }

    @Test
    public void verify_writeToFile_successfullySaveFile() throws IOException {
//        given
        ResponseEntity responseEntity = prepareResponse();
//        when
        Path logFile = responseStorage.writeToFile(responseEntity);
//        then
        assertTrue(Files.exists(logFile));
        assertTrue(Files.deleteIfExists(logFile));
    }

    private ResponseEntity prepareResponse() {
        return ResponseEntity.ok()
                .body(prepareResponseBodyJson());
    }

}