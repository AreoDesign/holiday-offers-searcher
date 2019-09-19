package com.home.ans.holidays.model.cdto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class RainbowOfferClientDto extends ClientDto {
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
