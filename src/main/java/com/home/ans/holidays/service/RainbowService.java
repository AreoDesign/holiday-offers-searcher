package com.home.ans.holidays.service;

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

import java.net.URI;

@Service
@Slf4j
public class RainbowService {

    private RestTemplate restTemplate;

    public ResponseEntity requestForOffers(URI url, HttpEntity requestEntity) {
        try {
            ResponseEntity response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );
            log.info("------------- RESPONSE BODY: -------------\n{}", response.getBody());
            return response;
        } catch (RestClientException ex) {
            log.error("There was an exception '{}' while processing request '{}' for URL: '{}', with root cause: \n{}",
                    ex.getClass().getSimpleName(), requestEntity, url, ex.getStackTrace());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Autowired
    @Qualifier("restTemplate")
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

}