package com.home.ans.holidays.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class RainbowOfferRequestBody {

    private Konfiguracja konfiguracja;
    private Sortowanie sortowanie;
    private boolean czyCenaZaWszystkich;
    private boolean czyGrupowac;
    private List<String> hotel;
    private String kategoriaHoteluMin;
    private String kategoriaHoteluMax;
    private List<String> miastaWyjazdu;
    private List<String> panstwa;
    private String terminWyjazduMin;
    private String terminWyjazduMax;
    private List<String> typyTransportu;
    private List<String> wyzywienia;
    private Paginacja paginacja;

    @Data
    @AllArgsConstructor
    public class Konfiguracja {
        private List<String> wiek;
        private String liczbaPokoi;
    }

    @Data
    @AllArgsConstructor
    public class Paginacja {
        private String przeczytane;
        private String iloscDoPobrania;
    }

    @Data
    @AllArgsConstructor
    public class Sortowanie {
        private boolean czyPoDacie;
        private boolean czyPoCenie;
        private boolean czyPoOcenach;
        private boolean czyPoPolecanych;
        private boolean czyDesc;
    }

}
