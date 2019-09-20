package com.home.ans.holidays.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("RAINBOW")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RainbowOfferEntity extends OfferEntity {
    private String source;
    private Double rating;
    private Integer promotionPercentage;

}
