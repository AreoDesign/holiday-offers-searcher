package com.home.ans.holidays.entity;

import com.home.ans.holidays.dictionary.BoardType;
import com.home.ans.holidays.dictionary.Destination;
import org.hibernate.annotations.DiscriminatorFormula;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "travel_offer")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorFormula("case when source = 'RAINBOW' then 'RAINBOW' else 'TUI' end")
public abstract class OfferEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime requestDate;

    private String offerCode;

    private String offerUrl;

    private LocalDateTime departureDateAndTime; // LocalDate type used for RainbowOffer!

    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    private Integer originalPerPersonPrice;

    private Integer discountPerPersonPrice;

    private Integer duration;

    private String hotelCode;

    private String hotelName;

    private Double hotelStandard;

    @Enumerated(EnumType.STRING)
    private Destination destination;
}
