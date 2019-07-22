package com.home.ans.holidays.component;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.home.ans.holidays.model.dto.RainbowPayload;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
@Data
public class RainbowRequest {
    //Beans to inject:
    private Gson gson;
    private DateTimeFormatter formatter;
    //Request customization fields:
    private int hotelStarsMin = 3;
    private int hotelStarsMax = 5;
    private List<String> countries = Arrays.asList("bulgaria", "chorwacja", "cypr", "cypr-polnocny", "czarnogora", "egipt", "grecja", "hiszpania", "maroko", "portugalia", "rumunia", "turcja", "wlochy");
    private LocalDate startDate = LocalDate.now(); // LocalDate.of(2019, 9, 1);
    private LocalDate endDate = null; // LocalDate.of(2019, 11, 14);
    private List<String> boardType = Arrays.asList("all-inclusive", "3-posilki", "2-posilki", "sniadania", "bez-wyzywienia");
    private int read = 0;
    private int toDownload = 18;

    public HttpEntity prepareHttpEntity(int read) {
        this.read = read;
        return prepareHttpEntity();
    }

    private HttpEntity prepareHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Lists.newArrayList(MediaType.APPLICATION_JSON));
        return new HttpEntity<>(gson.toJson(prepareBody()), headers);
    }

    private RainbowPayload prepareBody() {

        RainbowPayload requestBody = RainbowPayload.builder()
                .czyCenaZaWszystkich(false)
                .czyGrupowac(true)
                .hotel(ImmutableList.of("rodzinny"))
                .kategoriaHoteluMin(String.valueOf(hotelStarsMin))
                .kategoriaHoteluMax(String.valueOf(hotelStarsMax))
                .miastaWyjazdu(ImmutableList.of("warszawa"))
                .panstwa(ImmutableList.copyOf(countries))
                .terminWyjazduMin(startDate.format(formatter))
                .terminWyjazduMax(Objects.nonNull(endDate) ? endDate.format(formatter) : null)
                .typyTransportu(ImmutableList.of("air"))
                .wyzywienia(ImmutableList.copyOf(boardType))
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
                String.valueOf(read),
                String.valueOf(toDownload))
        );

        return requestBody;
    }

    @Autowired
    @Qualifier("gson")
    public void setGson(Gson gson) {
        this.gson = gson;
    }

    @Autowired
    @Qualifier("dateFormatter")
    public void setFormatter(DateTimeFormatter dateTimeFormatter) {
        this.formatter = dateTimeFormatter;
    }

}
