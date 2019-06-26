package com.home.ans.holidays.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.home.ans.holidays.entity.RainbowOfferEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
@Slf4j
public class ParserService {

    private Gson mapper;

    public List<RainbowOfferEntity> unparse(ResponseEntity response) {
        String json = (String) response.getBody();
        Type castType = new TypeToken<List<RainbowOfferEntity>>() {
        }.getType();
        List<RainbowOfferEntity> offers = mapper.fromJson(json, castType);

//        Hotel hotel = Hotel.builder()
//                .foreignId(bodyMap.get(JsonPojoTranslator.HOTEL_RAINDBOW_ID.getJson()))
//                .name(bodyMap.get(JsonPojoTranslator.HOTEL_NAME.getJson()))
//                .location(bodyMap.get(JsonPojoTranslator.HOTEL_LOCATION.getJson()))
//                .stars(Double.valueOf(bodyMap.get(JsonPojoTranslator.HOTEL_STARS.getJson())))
//                .customerRating(Double.valueOf(bodyMap.get(JsonPojoTranslator.HOTEL_CUSTOMER_RATING.getJson())))
//                .build();
//
//        Offer offer = Offer.builder()
//                .inquiryDate(Instant.ofEpochMilli(response.getHeaders().getDate()))
//                .foreignId(bodyMap.get(JsonPojoTranslator.OFFER_RAINBOW_ID.getJson()))
//                .travelDate(LocalDate.parse(bodyMap.get(JsonPojoTranslator.OFFER_TRAVEL_DATE.getJson())))
//                .url(bodyMap.get(JsonPojoTranslator.OFFER_URL.getJson()))
//                .stayDuration(Integer.valueOf(bodyMap.get(JsonPojoTranslator.OFFER_STAY_DURATION.getJson())))
//                .promotion(Integer.valueOf(bodyMap.get(JsonPojoTranslator.OFFER_PROMOTION.getJson())))
//                .startPrice(Integer.valueOf(bodyMap.get(JsonPojoTranslator.OFFER_START_PRICE.getJson())))
//                .actualPrice(Integer.valueOf(bodyMap.get(JsonPojoTranslator.OFFER_ACTUAL_PRICE.getJson())))
//                .build();
//
//        hotel.addOffer(offer);
//
//        return Pair.of(hotel, offer);
        return offers;
    }

    @Autowired
    @Qualifier("gson")
    public void setMapper(Gson gson) {
        this.mapper = gson;
    }

}
