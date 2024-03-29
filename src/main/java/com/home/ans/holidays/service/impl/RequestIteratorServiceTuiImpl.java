package com.home.ans.holidays.service.impl;

import com.google.common.collect.Lists;
import com.home.ans.holidays.component.TuiRequest;
import com.home.ans.holidays.converter.mapstruct.tui.TuiCdtoDtoConverter;
import com.home.ans.holidays.model.cdto.TuiOfferClientDto;
import com.home.ans.holidays.model.dto.TuiOfferDto;
import com.home.ans.holidays.service.ParserService;
import com.home.ans.holidays.service.RequestIteratorService;
import com.home.ans.holidays.service.ResponseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RequestIteratorServiceTuiImpl implements RequestIteratorService {

    private TuiRequest tuiRequest;
    private ResponseService responseService;
    private ParserService parserService;
    private TuiCdtoDtoConverter cdtoDtoConverter;

    @Override
    public Collection<TuiOfferDto> iterateRequests() {
        Collection<TuiOfferDto> dtos = Lists.newLinkedList();
        Collection<TuiOfferDto> singleShotOffers;
        ResponseEntity response;
        int cnt = 0;
        do {
            response = getResponse(tuiRequest, cnt);
            //emergency loop exit
            if (!response.getStatusCode().is2xxSuccessful()) break;
            singleShotOffers = convertToDto(response);
            dtos.addAll(singleShotOffers);
            cnt++;
        }
        while (response.getStatusCode().is2xxSuccessful() && !singleShotOffers.isEmpty());

        if (dtos.size() != 0) log.info("Successfully downloaded {} TUI offers.", dtos.size());
        else log.warn("No TUI offer matching given criteria downloaded!");

        return dtos;
    }

    private ResponseEntity getResponse(TuiRequest tuiRequest, int cnt) {
        return responseService.requestForOffers(tuiRequest.generateUrl(cnt), HttpMethod.GET, null);
    }

    private Collection<TuiOfferDto> convertToDto(ResponseEntity response) {
        Collection<TuiOfferClientDto> cdtos = (Collection<TuiOfferClientDto>) parserService.parse(response);
        return cdtos.stream()
                .map(cdtoDtoConverter::toDto)
                .collect(Collectors.toList());
    }

    @Autowired
    public void setTuiRequest(TuiRequest tuiRequest) {
        this.tuiRequest = tuiRequest;
    }

    @Autowired
    public void setResponseService(ResponseService responseService) {
        this.responseService = responseService;
    }

    @Autowired
    public void setParserService(ParserServiceTuiImpl parserService) {
        this.parserService = parserService;
    }

    @Autowired
    public void setCdtoDtoConverter(TuiCdtoDtoConverter cdtoDtoConverter) {
        this.cdtoDtoConverter = cdtoDtoConverter;
    }

}
