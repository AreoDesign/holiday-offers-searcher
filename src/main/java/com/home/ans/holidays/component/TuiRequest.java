package com.home.ans.holidays.component;

import com.google.gson.Gson;
import com.home.ans.holidays.dictionary.City;
import com.home.ans.holidays.entity.SearchCriteria;
import com.home.ans.holidays.exception.NoMatchingValueException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.net.URI;
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
public class TuiRequest extends Request {
    //static
    private static final String CORE_URL = "https://www.tui.pl/wypoczynek/wyniki-wyszukiwania-samolot?q=:price";
    private static final DateTimeFormatter TUI_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    //Request customization fields:
    private int page = 0;
    private int pageSize = 30;
    private URI searchUrl = generateUrl(this.page);

    public TuiRequest(SearchCriteria searchCriteria) {
        super(searchCriteria);
    }

    public URI generateUrl(int cnt) {
        StringBuilder url = new StringBuilder(CORE_URL);
        url.append(":byPlane:T");
        url.append(departureCities.stream()
                .map(Prefix.AIRPORT.getPrefix()::concat)
                .collect(Collectors.joining()));
        url.append(Prefix.DURATION_FROM.getPrefix()).append(minStayLength);
        url.append(Prefix.DURATION_TO.getPrefix()).append(maxStayLength);
        url.append(Prefix.START_DATE.getPrefix()).append(departureDateFrom.format(TUI_DATE_FORMATTER));
        url.append(Objects.nonNull(departureDateTo)
                ? Prefix.END_DATE.getPrefix() + departureDateTo.format(TUI_DATE_FORMATTER)
                : url.append(StringUtils.EMPTY));
        url.append(Prefix.ADULT_COUNT.getPrefix()).append(adultsCount);
        url.append(Prefix.CHILD_COUNT.getPrefix()).append(childrenCount);
        url.append(childrenBirthDates.stream()
                .map(s -> s.format(TUI_DATE_FORMATTER))
                .map(Prefix.BIRTH.getPrefix()::concat)
                .collect(Collectors.joining()));
        url.append(destinations.stream()
                .map(Prefix.CODE.getPrefix()::concat)
                .collect(Collectors.joining()));
        url.append(boardTypes.stream()
                .map(Prefix.BOARD_TYPE.getPrefix()::concat)
                .map(b -> b.replace(" ", "%2520"))
                .collect(Collectors.joining()));
        url.append(":amountRange:defaultAmountRange");
        url.append(":minHotelCategory:").append(minHotelStandard).append("s");
        url.append(":tripAdvisorRating:defaultTripAdvisorRating");
        url.append(":beach_distance:defaultBeachDistance");
        url.append(":tripType:WS&fullPrice=false&page=").append(cnt);
        return URI.create(url.toString());
    }

    @Getter
    @AllArgsConstructor
    private enum Destination {

        BULGARIA(Arrays.asList("BOJ", "BG")),
        CANARY_ISLANDS(Arrays.asList("FUE", "LPA", "GMZ", "ACE", "TFS", "IC")),
        CROATIA(Arrays.asList("SPU", "HR")),
        CYPRUS(Arrays.asList("LCA", "PFO", "CY")),
        EGYPT(Arrays.asList("HRG", "RMF", "SSH", "EG")),
        GREECE(Arrays.asList("ATH", "ATH3", "CFU", "KGS", "CHQ", "GPA", "RHO", "ZTH", "GR")),
        ITALY(Arrays.asList("BZN", "OLB", "CTA", "IT")),
        MOROCCO(Collections.singletonList("AGA")),
        MONTENEGRO(Collections.singletonList("TGD")),
        PORTUGAL(Arrays.asList("FNC", "FAO", "PT")),
        SPAIN(Arrays.asList("LEI", "BCN", "ALC", "GRO", "ALC2", "HEV", "AGP", "REU", "FXX", "IBZ", "PMI", "MAH", "ES")),
        TURKEY(Arrays.asList("AYT", "ADB", "TR"));

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
        ALL_INCLUSIVE("GT06-AI GT06-FBP"),
        FULL_BOARD("GT06-FB"),
        HALF_BOARD("GT06-HB"),
        BED_AND_BREAKFAST("GT06-BB"),
        OVER_NIGHT("GT06-AO");

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
        CRACOW("KRK"),
        GDANSK("GDN"),
        MODLIN("WMI"),
        WARSAW("WAW");

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
    private enum StayLength {
        FROM_3_TO_5(3, 5),
        FROM_6_TO_8(6, 8),
        FROM_9_TO_12(9, 12),
        FROM_13_TO_15(13, 15);

        private int min;
        private int max;

        public static StayLength getPeriod(int stayLength) {
            return Arrays.stream(values())
                    .filter(len -> len.getMin() <= stayLength && len.getMax() >= stayLength)
                    .findFirst()
                    .orElseThrow(NoMatchingValueException::new);
        }

        public static StayLength getDefault() {
            return FROM_6_TO_8;
        }
    }

    @Getter
    @AllArgsConstructor
    private enum Prefix {
        AIRPORT(":a:"),
        BIRTH(":birthDate:"),
        BOARD_TYPE(":board:"),
        CODE(":c:"),
        DURATION_FROM(":dF:"),
        DURATION_TO(":dT:"),
        START_DATE(":startDate:"),
        END_DATE(":endDate:"),
        ADULT_COUNT(":ctAdult:"),
        CHILD_COUNT(":ctChild:");

        private String prefix;
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
    protected Pair<Integer, Integer> getPeriodForStayLength(Integer stayLength) {
        try {
            StayLength period = StayLength.getPeriod(stayLength);
            return Pair.of(period.getMin(), period.getMax());
        } catch (NoMatchingValueException e) {
            log.warn("No valid period found for given stay length {}. Default values will be used: from {} to {}.",
                    stayLength, StayLength.getDefault().getMin(), StayLength.getDefault().getMax());
            return Pair.of(StayLength.getDefault().getMin(), StayLength.getDefault().getMax());
        }
    }

    @Autowired
    @Qualifier("gson")
    public void setGson(Gson gson) {
        this.gson = gson;
    }

}
