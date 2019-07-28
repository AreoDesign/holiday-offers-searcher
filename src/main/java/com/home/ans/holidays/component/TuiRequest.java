package com.home.ans.holidays.component;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.net.URI;
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
    private static final String CORE_URL = "https://www.tui.pl/wypoczynek/wyniki-wyszukiwania-samolot?q=:price";
    private static final DateTimeFormatter TUI_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
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
    private URI searchUrl = generateUrl(this.page);

    public URI generateUrl(int cnt) {
        String url = CORE_URL +
                ":byPlane:T" +
                departuresCodes.stream().map(Prefix.AIRPORT.getPrefix()::concat).collect(Collectors.joining()) +
                Prefix.DURATION_FROM.getPrefix() + durationFrom +
                Prefix.DURATION_TO.getPrefix() + durationTo +
                Prefix.START_DATE.getPrefix() + departureDateFrom.format(TUI_DATE_FORMATTER) +
                Prefix.ADULT_COUNT.getPrefix() + numberOfAdults +
                Prefix.CHILD_COUNT.getPrefix() + childrenBirthdays.size() +
                childrenBirthdays.stream().map(s -> s.format(TUI_DATE_FORMATTER)).map(Prefix.BIRTH.getPrefix()::concat).collect(Collectors.joining()) +
                destinationsCodes.stream().map(Prefix.CODE.getPrefix()::concat).collect(Collectors.joining()) +
                boardTypes.stream().map(Prefix.BOARD_TYPE.getPrefix()::concat).map(b -> b.replace(" ", "%2520")).collect(Collectors.joining()) +
                ":amountRange:defaultAmountRange" +
                ":minHotelCategory:" + minHotelStandard + "s" +
                ":tripAdvisorRating:defaultTripAdvisorRating" +
                ":beach_distance:defaultBeachDistance" +
                ":tripType:WS&fullPrice=false&page=" + cnt;
        return URI.create(url);
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

    @Getter
    @AllArgsConstructor
    public enum Prefix {
        AIRPORT(":a:"),
        BIRTH(":birthDate:"),
        BOARD_TYPE(":board:"),
        CODE(":c:"),
        DURATION_FROM(":dF:"),
        DURATION_TO(":dT:"),
        START_DATE(":startDate:"),
        ADULT_COUNT(":ctAdult:"),
        CHILD_COUNT(":ctChild:");

        private String prefix;
    }

    @Autowired
    @Qualifier("gson")
    public void setGson(Gson gson) {
        this.gson = gson;
    }

}
