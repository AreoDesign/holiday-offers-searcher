package com.home.ans.holidays.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ParserServiceTest {

    private final static String body = ""; //todo

    @Mock
    private ParserService parserService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void unparsingTest() {
        parserService.unparse(new ResponseEntity(HttpStatus.OK)); //todo
    }

}