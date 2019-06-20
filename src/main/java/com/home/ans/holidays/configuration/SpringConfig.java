package com.home.ans.holidays.configuration;

import com.home.ans.holidays.component.TravelRequest;
import com.home.ans.holidays.dictionary.Request;
import com.home.ans.holidays.entity.OfferEntity;
import com.home.ans.holidays.repository.OfferRepository;
import com.home.ans.holidays.service.RainbowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Instant;

@Configuration
@EnableScheduling
public class SpringConfig {

    private RainbowService rainbowService;
    private TravelRequest travelRequest;
    private OfferRepository offerRepository;

    @Scheduled(cron = "0 0 4,8,12,16,20 * * ?")
    public void requestForTravelOffer() {
        ResponseEntity response = rainbowService.requestForOffers(
                Request.RAINBOW_TOURS.getUrl(),
                travelRequest.prepareHttpEntity()
        );

        response.getBody();

        OfferEntity offerEntity = OfferEntity.builder()
                .inquiryDate(Instant.ofEpochMilli(response.getHeaders().getDate()))
                .build();

        offerRepository.save(offerEntity);
    }

    @Autowired
    public void setRainbowService(RainbowService rainbowService) {
        this.rainbowService = rainbowService;
    }

    @Autowired
    public void setTravelRequest(TravelRequest travelRequest) {
        this.travelRequest = travelRequest;
    }

    @Autowired
    public void setOfferRepository(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }
}
