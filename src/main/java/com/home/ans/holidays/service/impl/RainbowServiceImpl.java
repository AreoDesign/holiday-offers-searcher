package com.home.ans.holidays.service.impl;

import com.home.ans.holidays.service.RainbowService;
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
import java.util.Objects;

@Service
@Slf4j
public class RainbowServiceImpl implements RainbowService {

    private RestTemplate restTemplate;

    public ResponseEntity requestForOffers(URI url, HttpEntity requestEntity) {
        try {
            ResponseEntity response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );
            logResponseStatus(response);
            return response;
        } catch (RestClientException ex) {
            log.error("There was an exception '{}' while processing request '{}' for URL: '{}', with root cause: \n{}",
                    ex.getClass().getSimpleName(), requestEntity, url, ex.getStackTrace());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    private void logResponseStatus(ResponseEntity response) {
        if (Objects.isNull(response.getBody())) {
            log.error("Something went wrong - response body is null!");
        } else {
            log.info("Response returned with status: {}\n------------- RESPONSE BODY: -------------\n{}",
                    response.getStatusCodeValue(), response.getBody());
        }
    }

    @Autowired
    @Qualifier("restTemplate")
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

}