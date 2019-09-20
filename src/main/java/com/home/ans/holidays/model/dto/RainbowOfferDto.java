package com.home.ans.holidays.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class RainbowOfferDto extends OfferDto {
    private Double rating;
    private Integer promotionPercentage;

    @Builder
    public RainbowOfferDto(LocalDateTime requestDate, String offerCode, String offerUrl, LocalDate departureDateAndTime, String boardType, Integer originalPerPersonPrice, Integer discountPerPersonPrice, Integer duration, Integer hotelCode, String hotelName, Double hotelStandard, String destination, String source, Double rating, Integer promotionPercentage) {
        super(requestDate, offerCode, offerUrl, departureDateAndTime, boardType, originalPerPersonPrice, discountPerPersonPrice, duration, hotelCode, hotelName, hotelStandard, destination, source);
        this.rating = rating;
        this.promotionPercentage = promotionPercentage;
    }
}
