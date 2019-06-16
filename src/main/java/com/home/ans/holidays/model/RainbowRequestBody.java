package com.home.ans.holidays.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class RainbowRequestBody {

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

}
