package com.home.ans.holidays.service.impl;

import com.home.ans.holidays.component.ResponseStorage;
import com.home.ans.holidays.service.ResponseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;

@Service
@Slf4j
public class ResponseServiceImpl implements ResponseService {

    private RestTemplate restTemplate;
    private ResponseStorage responseStorage;

    @Override
    public ResponseEntity requestForOffers(URI url, HttpEntity requestEntity) {
        try {
            ResponseEntity response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );
            responseStorage.logStatus(response);
            responseStorage.writeToFile(response);
            responseStorage.cleanLogs(7);
            return response;
        } catch (RestClientException ex) {
            log.error("There was an exception '{}' while processing request '{}' for URL: '{}', with root cause: \n{}",
                    ex.getClass().getSimpleName(), requestEntity, url, ex.getStackTrace());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
    }

    @Autowired
    @Qualifier("restTemplate")
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    public void setResponseStorage(ResponseStorage responseStorage) {
        this.responseStorage = responseStorage;
    }
}