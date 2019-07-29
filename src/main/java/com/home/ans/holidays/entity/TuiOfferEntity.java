package com.home.ans.holidays.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;


@Entity
@Table(name = "oferta_tui")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TuiOfferEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime requestDate;
    private String offerCode;
    private String offerUrl;
    private LocalDateTime departureDateAndTime;
    private String boardType; // TODO: 2019-07-22 go to enum and attribute converter
    private Integer originalPerPersonPrice;
    private Integer discountPerPersonPrice;
    private Integer duration;
    private String hotelCode;
    private String hotelName;
    private Double hotelStandard;
    private String destination;

}
