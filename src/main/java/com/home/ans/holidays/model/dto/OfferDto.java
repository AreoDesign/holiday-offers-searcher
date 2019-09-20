package com.home.ans.holidays.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public abstract class OfferDto {
    protected LocalDateTime requestDate;
    protected String offerCode;
    protected String offerUrl;
    protected LocalDate departureDateAndTime;
    protected String boardType;
    protected Integer originalPerPersonPrice;
    protected Integer discountPerPersonPrice;
    protected Integer duration;
    protected Integer hotelCode;
    protected String hotelName;
    protected Double hotelStandard;
    protected String destination;
    protected String source;
}
