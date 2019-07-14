package com.home.ans.holidays.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "oferta_rainbow")
@Data
@Builder
public class RainbowOfferEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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
