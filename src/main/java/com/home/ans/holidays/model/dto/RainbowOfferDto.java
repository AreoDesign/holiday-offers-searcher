package com.home.ans.holidays.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class RainbowOfferDto extends OfferDto {
    private LocalDateTime dataZapytania;
    private String ofertaId;
    private String ofertaUrl;
    private LocalDate dataWKodzieProduktu;
    private String wyzywienie;
    private Double ocenaOgolna;
    private Integer cenaPrzedPromocja;
    private Integer cenaAktualna;
    private Integer liczbaDni;
    private Integer procentPromocji;
    private Integer hotelId;
    private String nazwaHotelu;
    private Double gwiazdkiHotelu;
    private String lokalizacja;
}
