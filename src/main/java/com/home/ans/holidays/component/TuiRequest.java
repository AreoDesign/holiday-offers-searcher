package com.home.ans.holidays.component;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.home.ans.holidays.model.dto.TuiPayload;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Data
public class TuiRequest {
    //Beans to inject:
    private Gson gson;
    //static
    private static DateTimeFormatter TUI_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static String SITE = "wypoczynek/wyniki-wyszukiwania-samolot";
    private static String OFFER_TYPE = "BY_PLANE";
    //Request customization fields:
    private List<LocalDate> childrenBirthdays = Collections.singletonList(LocalDate.of(2019, 3, 5));
    private List<String> departuresCodes = Collections.singletonList("WAW");
    private List<String> destinationsCodes = Destination.getAllRegions();
    private int numberOfAdults = 2;
    private LocalDate departureDateFrom = LocalDate.now();
    private int durationFrom = 6;
    private int durationTo = 8;
    private List<String> boardTypes = BoardType.getAllCodes();
    private int minHotelStandard = 3;
    private int page = 0;
    private int pageSize = 30;

    public HttpEntity prepareHttpEntity(int read) {
        this.page = read;
        return prepareHttpEntity();
    }

    private HttpEntity prepareHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Lists.newArrayList(MediaType.APPLICATION_JSON));
        return new HttpEntity<>(gson.toJson(prepareBody()), headers);
    }

    private TuiPayload prepareBody() {

        TuiPayload requestBody = TuiPayload.builder()
                .offerType(OFFER_TYPE)
                .childrenBirthdays(childrenBirthdays.stream().map(birthday -> birthday.format(TUI_DATE_FORMATTER)).collect(Collectors.toList()))
                .departuresCodes(departuresCodes)
                .destinationsCodes(destinationsCodes)
                .numberOfAdults(numberOfAdults)
                .departureDateFrom(departureDateFrom.format(TUI_DATE_FORMATTER))
                .durationFrom(String.valueOf(durationFrom))
                .durationTo(String.valueOf(durationTo))
                .site(SITE)
                .build();

        requestBody.setMetadata(requestBody.new Metadata(page, pageSize, "price"));

        requestBody.setFilters(
                ImmutableList.of(
                        requestBody.new Filter("board", boardTypes),
                        requestBody.new Filter("amountRange", Collections.singletonList("defaultAmountRange")),
                        requestBody.new Filter("minHotelCategory", Collections.singletonList(minHotelStandard + "s")),
                        requestBody.new Filter("tripAdvisorRating", Collections.singletonList("defaultTripAdvisorRating")),
                        requestBody.new Filter("beach_distance", Collections.singletonList("defaultBeachDistance"))
                )
        );

        return requestBody;
    }

    @Getter
    @AllArgsConstructor
    public enum Destination {

        BULGARIA(Arrays.asList("BOJ", "BG")),
        CANARY_ISLANDS(Arrays.asList("FUE", "LPA", "GMZ", "ACE", "TFS", "IC")),
        CROATIA(Arrays.asList("SPU", "HR")),
        CYPRUS(Arrays.asList("LCA", "PFO", "CY")),
        EGYPT(Arrays.asList("HRG", "RMF", "SSH", "EG")),
        GREECE(Arrays.asList("ATH", "ATH3", "CFU", "KGS", "CHQ", "GPA", "RHO", "ZTH", "GR")),
        ITALY(Arrays.asList("BZN", "OLB", "CTA", "IT")),
        MAROCCO(Collections.singletonList("AGA")),
        MONTENEGRO(Collections.singletonList("TGD")),
        PORTUGAL(Arrays.asList("FNC", "FAO", "PT")),
        SPAIN(Arrays.asList("LEI", "BCN", "ALC", "GRO", "ALC2", "HEV", "AGP", "REU", "FXX", "IBZ", "PMI", "MAH", "ES")),
        TURKEY(Arrays.asList("AYT", "ADB", "TR"));

        private List<String> regions;

        public static List<String> getRegionsForCountries(Set<Destination> countries) {
            return Arrays.stream(values())
                    .filter(countries::contains)
                    .map(Destination::getRegions)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
        }

        public static List<String> getAllRegions() {
            return Arrays.stream(values())
                    .map(Destination::getRegions)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
        }
    }

    @Getter
    @AllArgsConstructor
    public enum BoardType {
        ALL_INCLUSIVE("GT06-AI GT06-FBP"),
        FULL_BOARD("GT06-FB"),
        HALF_BOARD("GT06-HB"),
        BED_AND_BREAKFAST("GT06-BB"),
        OVER_NIGHT("GT06-AO");

        private String code;

        public static List<String> getCodesForBoardTypes(Set<BoardType> boardTypes) {
            return Arrays.stream(values())
                    .filter(boardTypes::contains)
                    .map(BoardType::getCode)
                    .collect(Collectors.toList());
        }

        public static List<String> getAllCodes() {
            return Arrays.stream(values())
                    .map(BoardType::getCode)
                    .collect(Collectors.toList());
        }
    }

    @Autowired
    @Qualifier("gson")
    public void setGson(Gson gson) {
        this.gson = gson;
    }

}
