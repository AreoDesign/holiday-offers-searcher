package com.home.ans.holidays.controller;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.home.ans.holidays.model.Konfiguracja;
import com.home.ans.holidays.model.Paginacja;
import com.home.ans.holidays.model.RainbowRequestBody;
import com.home.ans.holidays.model.Sortowanie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/travel")
@Slf4j
public class RainbowClient {

    private static final String API_URL = "https://rpl-api.r.pl/szukaj/api/wyszukaj";

    // push to @Configuration @Bean external class
    // use CommandLineRunner interface implementation!!!
    private RestTemplate restTemplate = new RestTemplate();
    private Gson gson = new Gson();
    private DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    @GetMapping
    public ResponseEntity bypass() {
        RainbowRequestBody body = prepareBody();
        HttpEntity requestEntity = prepareHttpEntity(body);
        ResponseEntity<String> responseRaw = restTemplate.exchange(
                API_URL,
                HttpMethod.POST,
                requestEntity,
                String.class);
        log.info("------------- RESPONSE BODY: -------------\n{}", responseRaw.getBody());
        return ResponseEntity.ok(responseRaw);
    }

    private HttpEntity prepareHttpEntity(RainbowRequestBody body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Lists.newArrayList(MediaType.APPLICATION_JSON));
        return new HttpEntity<>(gson.toJson(body), headers);
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
}
