package com.home.ans.holidays.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import java.net.URI;

public interface RainbowService {
    ResponseEntity requestForOffers(URI url, HttpEntity requestEntity);
}
