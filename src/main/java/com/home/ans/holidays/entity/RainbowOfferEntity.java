package com.home.ans.holidays.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

@Entity
@Table(name = "oferta_rainbow")
@Data
public class RainbowOfferEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private Instant dataZapytania;

    private String ofertaId;
    private String ofertaUrl;
    private String dataWKodzieProduktu;
    private String cenaPrzedPromocja;
    private String cenaAktualna;
    private String liczbaDni;
    private String procentPromocji;
    private String hotelId;
    private String nazwaHotelu;
    private String gwiazdkiHotelu;
    private String ocenaOgolna;
    private String lokalizacja;


}
