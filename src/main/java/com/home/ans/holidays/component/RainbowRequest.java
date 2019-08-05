package com.home.ans.holidays.component;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.home.ans.holidays.dictionary.City;
import com.home.ans.holidays.entity.SearchCriteria;
import com.home.ans.holidays.model.dto.RainbowPayload;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Data
@EqualsAndHashCode(callSuper = true)
@Slf4j
public class RainbowRequest extends Request {
    //Beans to inject:
    private DateTimeFormatter formatter;
    //Request customization fields:
    private int read = 0;
    private int toDownload = 18;

    public RainbowRequest(SearchCriteria searchCriteria) {
        super(searchCriteria);
    }

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
                .hotel(HotelType.getAll())
                .kategoriaHoteluMin(String.valueOf(minHotelStandard))
                .kategoriaHoteluMax(String.valueOf(5))
                .miastaWyjazdu(ImmutableList.copyOf(departureCities))
                .panstwa(ImmutableList.copyOf(destinations))
                .terminWyjazduMin(departureDateFrom.format(formatter))
                .terminWyjazduMax(Objects.nonNull(departureDateTo) ? departureDateTo.format(formatter) : null)
                .typyTransportu(ImmutableList.of("air"))
                .wyzywienia(ImmutableList.copyOf(boardTypes))
                .build();

        requestBody.setKonfiguracja(requestBody.new Konfiguracja(
                ImmutableList.of(
                        LocalDate.of(1985, 1, 4).format(formatter),
                        LocalDate.of(1988, 3, 17).format(formatter),
                        LocalDate.of(2019, 3, 5).format(formatter)),
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

    @Getter
    @AllArgsConstructor
    private enum Destination {
        BULGARIA(Collections.singletonList("bulgaria")),
        CROATIA(Collections.singletonList("chorwacja")),
        CYPRUS(Arrays.asList("cypr", "cypr-polnocny")),
        EGYPT(Collections.singletonList("egipt")),
        GREECE(Collections.singletonList("grecja")),
        ITALY(Collections.singletonList("wlochy")),
        MOROCCO(Collections.singletonList("maroko")),
        MONTENEGRO(Collections.singletonList("czarnogora")),
        PORTUGAL(Collections.singletonList("portugalia")),
        ROMANIA(Collections.singletonList("rumunia")),
        SPAIN(Collections.singletonList("hiszpania")),
        TURKEY(Collections.singletonList("turcja"));

        private List<String> regions;

        public static Set<String> translateValues(Collection<com.home.ans.holidays.dictionary.Destination> countries) {
            return Arrays.stream(values())
                    .filter(countries::contains)
                    .map(Destination::getRegions)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toSet());
        }
    }


    @Getter
    @AllArgsConstructor
    private enum BoardType {
        ALL_INCLUSIVE("all-inclusive"),
        FULL_BOARD("3-posilki"),
        HALF_BOARD("2-posilki"),
        BED_AND_BREAKFAST("sniadania"),
        OVER_NIGHT("bez-wyzywienia");

        private String code;

        public static Set<String> translateValues(Collection<com.home.ans.holidays.dictionary.BoardType> boardTypes) {
            return Arrays.stream(values())
                    .filter(boardTypes::contains)
                    .map(BoardType::getCode)
                    .collect(Collectors.toSet());
        }
    }


    @Getter
    @AllArgsConstructor
    private enum Airport {
        CRACOW("krakow"),
        GDANSK("gdansk"),
        LODZ("lodz"),
        WARSAW("warszawa");

        private String airportCode;

        public static Set<String> translateValues(Collection<com.home.ans.holidays.dictionary.City> cities) {
            return Arrays.stream(values())
                    .filter(cities::contains)
                    .map(Airport::getAirportCode)
                    .collect(Collectors.toSet());
        }
    }


    @Getter
    @AllArgsConstructor
    private enum HotelType {
        FAMILY_HOTEL("rodzinny"),
        ADULTS_HOTEL("dla-doroslych"),
        WHITE_OLIVE_HOTEL("white-olive"),
        SEPARATED_BEDROOM_APARTMENT("apartament-z-osobna-sypialnia"),
        SEPARATED_BEDROOM_ROOM("z-osobna-sypialnia"),
        FAMILY_ROOM("pokoj-rodzinny"),
        STUDIO_ROOM("studio"),
        SEA_VIEW_ROOM("z-widokiem-na-morze");

        private String description;

        public static List<String> getAll() {
            return Arrays.stream(values())
                    .map(HotelType::getDescription)
                    .collect(Collectors.toList());
        }
    }

    @Override
    protected Collection<String> getRegionsForDestinations(Collection<com.home.ans.holidays.dictionary.Destination> destinations) {
        return Destination.translateValues(destinations);
    }

    @Override
    protected Collection<String> getCodesForBoardTypes(Collection<com.home.ans.holidays.dictionary.BoardType> boardTypes) {
        return BoardType.translateValues(boardTypes);
    }

    @Override
    protected Collection<String> getAirportCodesForDepartureCities(Collection<City> cities) {
        return Airport.translateValues(cities);
    }

    @Override
    protected Pair<Integer, Integer> getPeriodForStayLength(@Nullable Integer stayLength) {
        if (stayLength > 0 && stayLength <= 22) {
            return Pair.of(stayLength, 22);
        } else {
            log.warn("Given number of {} is beyond valid scope. Defaults of max available scope for Rainbow possible assigned 1-22!", stayLength);
            return Pair.of(1, 22);
        }
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
