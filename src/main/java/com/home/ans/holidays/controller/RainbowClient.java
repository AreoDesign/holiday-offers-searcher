package com.home.ans.holidays.controller;

import com.home.ans.holidays.component.TravelRequest;
import com.home.ans.holidays.dictionary.Request;
import com.home.ans.holidays.service.RainbowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/travel")
public class RainbowClient {

    private RainbowService rainbowService;
    private TravelRequest travelRequest;

    @GetMapping("/single-shot-request")
    public HttpStatus singleShotRequest() {
        ResponseEntity response = this.getDefaultResponse();
        //todo: implement here
        return HttpStatus.OK;
    }

    @GetMapping("/default")
    public ResponseEntity getDefaultResponse() {
        return rainbowService.requestForOffers(Request.RAINBOW_TOURS.getUrl(), travelRequest.prepareHttpEntity());
    }

    @GetMapping("/custom")
    public ResponseEntity getCustomResponse(HttpEntity requestEntity) {
        return rainbowService.requestForOffers(Request.RAINBOW_TOURS.getUrl(), requestEntity);
    }

    @Autowired
    public void setRainbowService(RainbowService rainbowService) {
        this.rainbowService = rainbowService;
    }

    @Autowired
    public void setTravelRequest(TravelRequest travelRequest) {
        this.travelRequest = travelRequest;
    }

}
