package com.home.ans.holidays.model.cdto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public abstract class ClientDto {
    protected LocalDateTime requestDate;
    protected String offerCode;
    protected String offerUrl;
    protected String departureDateAndTime;
    protected String boardType;
    protected Integer originalPerPersonPrice;
    protected Integer discountPerPersonPrice;
    protected Integer duration;
    protected String hotelCode;
    protected String hotelName;
    protected Double hotelStandard;
    protected String destination;
}
