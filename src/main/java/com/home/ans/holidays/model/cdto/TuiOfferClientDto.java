package com.home.ans.holidays.model.cdto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class TuiOfferClientDto extends ClientDto {
    private LocalDateTime requestDate;
    private String offerCode;
    private String offerUrl;
    private String departureDateAndTime;
    private String boardType;
    private Integer originalPerPersonPrice;
    private Integer discountPerPersonPrice;
    private Integer duration;
    private String hotelCode;
    private String hotelName;
    private Double hotelStandard;
    private String destination;
}
