package com.home.ans.holidays.model.cdto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class RainbowOfferClientDto extends ClientDto {
    private String rating;
    private Integer promotionPercentage;

    @Builder
    public RainbowOfferClientDto(LocalDateTime requestDate, String offerCode, String offerUrl, String departureDateAndTime, String boardType, Integer originalPerPersonPrice, Integer discountPerPersonPrice, Integer duration, String hotelCode, String hotelName, Double hotelStandard, String destination, String rating, Integer promotionPercentage) {
        super(requestDate, offerCode, offerUrl, departureDateAndTime, boardType, originalPerPersonPrice, discountPerPersonPrice, duration, hotelCode, hotelName, hotelStandard, destination);
        this.rating = rating;
        this.promotionPercentage = promotionPercentage;
    }
}
