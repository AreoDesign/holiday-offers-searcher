package com.home.ans.holidays.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("RAINBOW")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RainbowOfferEntity extends OfferEntity {
    private String source;
    private Double ocenaOgolna;
    private Integer procentPromocji;

}
