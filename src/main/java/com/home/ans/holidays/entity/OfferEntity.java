package com.home.ans.holidays.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

@Entity
@Table(name = "offer")
@Data
public class OfferEntity {

    @Builder
    public OfferEntity(@NotBlank Instant inquiryDate) {
        this.inquiryDate = inquiryDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private Instant inquiryDate;
}
