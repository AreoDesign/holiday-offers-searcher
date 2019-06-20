package com.home.ans.holidays.component;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.home.ans.holidays.model.Konfiguracja;
import com.home.ans.holidays.model.Paginacja;
import com.home.ans.holidays.model.RainbowRequestBody;
import com.home.ans.holidays.model.Sortowanie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class TravelRequest {
    private Gson jsonPojoMapper;
    private DateTimeFormatter formatter;

    public HttpEntity prepareHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Lists.newArrayList(MediaType.APPLICATION_JSON));
        return new HttpEntity<>(jsonPojoMapper.toJson(prepareBody()), headers);
    }

    private RainbowRequestBody prepareBody() {
        return RainbowRequestBody.builder()
                .konfiguracja(Konfiguracja.builder()
                        .wiek(ImmutableList.of(
                                LocalDate.of(1985, 1, 1).format(formatter),
                                LocalDate.of(1988, 1, 1).format(formatter),
                                LocalDate.of(2019, 1, 1).format(formatter)))
                        .liczbaPokoi(String.valueOf(1))
                        .build())
                .sortowanie(Sortowanie.builder()
                        .czyPoDacie(false)
                        .czyDesc(false)
                        .czyPoCenie(true)
                        .czyPoOcenach(false)
                        .czyPoPolecanych(false)
                        .build())
                .czyCenaZaWszystkich(false)
                .czyGrupowac(true)
                .hotel(ImmutableList.of("rodzinny"))
                .kategoriaHoteluMin(String.valueOf(3))
                .kategoriaHoteluMax(String.valueOf(5))
                .miastaWyjazdu(ImmutableList.of("warszawa"))
                .panstwa(ImmutableList.of("grecja"))
                .terminWyjazduMin(LocalDate.of(2019, 9, 1).format(formatter))
                .terminWyjazduMax(LocalDate.of(2019, 10, 14).format(formatter))
                .typyTransportu(ImmutableList.of("air"))
                .wyzywienia(ImmutableList.of("all-inclusive"))
                .paginacja(Paginacja.builder()
                        .przeczytane(String.valueOf(0))
                        .iloscDoPobrania(String.valueOf(18))
                        .build())
                .build();
    }

    @Autowired
    @Qualifier("gson")
    public void setJsonPojoMapper(Gson gson) {
        this.jsonPojoMapper = gson;
    }

    @Autowired
    @Qualifier("dateTimeFormatter")
    public void setFormatter(DateTimeFormatter dateTimeFormatter) {
        this.formatter = dateTimeFormatter;
    }

}
