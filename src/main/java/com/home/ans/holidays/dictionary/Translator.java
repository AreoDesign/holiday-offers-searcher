package com.home.ans.holidays.dictionary;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum Translator {

    OFERTA_ID("oferta_id", "ID", null),
    OFERTA_URL("oferta_url", "OfertaUrl", null),
    DATA_W_KODZIE_PRODUKTU("data_w_kodzie_produktu", "DataWKodzieProduktu", null),
    OCENA_OGOLNA("ocena_ogolna", "OcenaOgolna", "Opinie"),
    CENA_PRZED_PROMOCJA("cena_przed_promocja", "CenaPrzedPromocja", "Ceny"),
    CENA_AKTUALNA("cena_aktualna", "CenaAktualna", "Ceny"),
    LICZBA_DNI("liczba_dni", "LiczbaDni", "Ceny"),
    PROCENT_PROMOCJI("procent_promocji", "ProcentPromocji", "Ceny"),
    HOTEL_ID("hotel_id", "HotelId", "Blok1"),
    NAZWA_HOTELU("nazwa_hotelu", "NazwaHotelu", "Blok1"),
    GWIAZDKI_HOTELU("gwiazdki_hotelu", "GwiazdkiHotelu", "Blok1"),
    LOKALIZACJA("lokalizacja", "Lokalizacja", "Blok1");

    private String dbColumnName;
    private String responseKey;
    @Nullable
    private String nestName;

    public static List<String> getResponseKeys() {
        return Arrays.stream(Translator.values())
                .map(Translator::getResponseKey)
                .collect(Collectors.toList());
    }

    public static List<String> getNestedResponseKeys() {
        return Arrays.stream(Translator.values())
                .map(Translator::getNestName)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
