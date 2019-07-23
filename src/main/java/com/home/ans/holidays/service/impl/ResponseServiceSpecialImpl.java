package com.home.ans.holidays.service.impl;

import com.home.ans.holidays.component.ResponseStorage;
import com.home.ans.holidays.component.TuiRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.stream.Collectors;

import static com.home.ans.holidays.component.TuiRequest.TUI_DATE_FORMATTER;

@Service
@Slf4j
public class ResponseServiceSpecialImpl {

    private static final String CORE_URL = "https://www.tui.pl/wypoczynek/wyniki-wyszukiwania-samolot?q=:price";
    private static final String CODE_PREFIX = ":c:";
    private static final String BOARD_TYPE_PREFIX = ":board:";

    private RestTemplate restTemplate;
    private ResponseStorage responseStorage;

    public ResponseEntity requestForOffers(TuiRequest tuiRequest) {
        URI url = generateUrl(tuiRequest);
        try {
            ResponseEntity response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    String.class
            );
            responseStorage.logStatus(response);
            responseStorage.writeToFile(response);
            responseStorage.cleanLogs(7);
            return response;
        } catch (RestClientException ex) {
            log.error("There was an exception '{}' while processing request for URL: '{}', with root cause: \n{}",
                    ex.getClass().getSimpleName(), url, ex.getStackTrace());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
    }

    private URI generateUrl(TuiRequest tuiRequest) {
        String url = CORE_URL +
                ":byPlane:T" +
                ":a:" + tuiRequest.getDeparturesCodes() +
                ":dF:" + tuiRequest.getDurationFrom() +
                ":dT:" + tuiRequest.getDurationTo() +
                ":startDate:" + tuiRequest.getDepartureDateFrom().format(TUI_DATE_FORMATTER) +
                ":ctAdult:" + tuiRequest.getNumberOfAdults() +
                ":ctChild:" + tuiRequest.getChildrenBirthdays().size() +
                tuiRequest.getDestinationsCodes().stream().map(CODE_PREFIX::concat).collect(Collectors.joining()) +
                tuiRequest.getBoardTypes().stream().map(BOARD_TYPE_PREFIX::concat).map(b -> b.replace(" ", "%2520")).collect(Collectors.joining()) +
                ":amountRange:defaultAmountRange" +
                ":minHotelCategory:" + tuiRequest.getMinHotelStandard() + "s" +
                ":tripAdvisorRating:defaultTripAdvisorRating" +
                ":beach_distance:defaultBeachDistance" +
                ":tripType:WS&fullPrice=false&page=0";
        return URI.create(url);
    }

    @Autowired
    @Qualifier("restTemplate")
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    public void setResponseStorage(ResponseStorage responseStorage) {
        this.responseStorage = responseStorage;
    }
}