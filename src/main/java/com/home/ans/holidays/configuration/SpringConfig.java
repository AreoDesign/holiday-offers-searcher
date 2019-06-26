package com.home.ans.holidays.configuration;

import com.home.ans.holidays.component.TravelRequest;
import com.home.ans.holidays.dictionary.Request;
import com.home.ans.holidays.repository.RainbowOfferRepository;
import com.home.ans.holidays.service.ParserService;
import com.home.ans.holidays.service.RainbowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;

@Configuration
@EnableScheduling
public class SpringConfig {

    private RainbowService rainbowService;
    private TravelRequest travelRequest;
    private ParserService parserService;
    private RainbowOfferRepository rainbowOfferRepository;
//    private HotelRepository hotelRepository;


    @Scheduled(cron = "0 0 4,8,12,16,20 * * ?")
    public void requestForTravelOffer() throws IOException {
        ResponseEntity response = rainbowService.requestForOffers(
                Request.RAINBOW_TOURS.getUrl(),
                travelRequest.prepareHttpEntity()
        );

        //todo: here parsing to simple Raw object
//
//        Pair<Hotel, Offer> entities = parserService.unparseOntoPair(response);
//        Hotel hotel = hotelRepository.save(entities.getFirst());
//        hotel.addOffer();
//        rainbowOfferRepository.save(entities.getSecond());

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
    public void setParserService(ParserService parserService) {
        this.parserService = parserService;
    }

    @Autowired
    public void setRainbowOfferRepository(RainbowOfferRepository rainbowOfferRepository) {
        this.rainbowOfferRepository = rainbowOfferRepository;
    }

//    @Autowired
//    public void setHotelRepository(HotelRepository hotelRepository) {
//        this.hotelRepository = hotelRepository;
//    }

}
