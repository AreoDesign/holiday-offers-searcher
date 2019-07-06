package com.home.ans.holidays.component;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.home.ans.holidays.model.dto.RainbowOfferRequestBody;
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
    private Gson gson;
    private DateTimeFormatter formatter;

    public HttpEntity prepareHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Lists.newArrayList(MediaType.APPLICATION_JSON));
        return new HttpEntity<>(gson.toJson(prepareBody()), headers);
    }

    private RainbowOfferRequestBody prepareBody() {
        RainbowOfferRequestBody requestBody = RainbowOfferRequestBody.builder()
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
                .build();

        requestBody.setKonfiguracja(requestBody.new Konfiguracja(
                ImmutableList.of(
                        LocalDate.of(1985, 1, 1).format(formatter),
                        LocalDate.of(1988, 1, 1).format(formatter),
                        LocalDate.of(2019, 1, 1).format(formatter)),
                String.valueOf(1))
        );

        requestBody.setSortowanie(requestBody.new Sortowanie(
                false,
                true,
                false,
                false,
                false)
        );

        requestBody.setPaginacja(requestBody.new Paginacja(
                String.valueOf(0),
                String.valueOf(18))
        );

        return requestBody;
    }

    @Autowired
    @Qualifier("gson")
    public void setGson(Gson gson) {
        this.gson = gson;
    }

    @Autowired
    @Qualifier("dateTimeFormatter")
    public void setFormatter(DateTimeFormatter dateTimeFormatter) {
        this.formatter = dateTimeFormatter;
    }

}
