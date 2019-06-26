package com.home.ans.holidays.service;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
public class ResponseEntityForTests {

    private static final ResponseEntity RESPONSE_ENTITY = new ResponseEntity(HttpStatus.OK); //todo
}
