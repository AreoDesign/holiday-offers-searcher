package com.home.ans.holidays.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.net.URI;

public interface ResponseService {

    ResponseEntity requestForOffers(URI url, HttpMethod method, HttpEntity requestEntity);

}
