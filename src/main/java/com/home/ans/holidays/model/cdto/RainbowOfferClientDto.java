package com.home.ans.holidays.model.cdto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class RainbowOfferClientDto {
    private LocalDateTime dataZapytania;
    private String ofertaId;
    private String ofertaUrl;
    private String dataWKodzieProduktu;
    private String wyzywienie;
    private String ocenaOgolna;
    private Integer cenaPrzedPromocja;
    private Integer cenaAktualna;
    private Integer liczbaDni;
    private Integer procentPromocji;
    private Integer hotelId;
    private String nazwaHotelu;
    private Double gwiazdkiHotelu;
    private String lokalizacja;
}
