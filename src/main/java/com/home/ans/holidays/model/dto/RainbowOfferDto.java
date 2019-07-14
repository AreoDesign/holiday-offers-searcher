package com.home.ans.holidays.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class RainbowOfferDto {
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
