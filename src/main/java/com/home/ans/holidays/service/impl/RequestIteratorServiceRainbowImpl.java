package com.home.ans.holidays.service.impl;

import com.google.common.collect.Lists;
import com.home.ans.holidays.component.RainbowRequest;
import com.home.ans.holidays.converter.mapstruct.rainbow.RainbowCdtoDtoConverter;
import com.home.ans.holidays.dictionary.Request;
import com.home.ans.holidays.model.cdto.RainbowOfferClientDto;
import com.home.ans.holidays.model.dto.RainbowOfferDto;
import com.home.ans.holidays.service.ParserService;
import com.home.ans.holidays.service.RequestIteratorService;
import com.home.ans.holidays.service.ResponseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RequestIteratorServiceRainbowImpl implements RequestIteratorService {

    private RainbowRequest rainbowRequest;
    private ParserService parserService;
    private ResponseService responseService;
    private RainbowCdtoDtoConverter cdtoDtoConverter;

    @Override
    public Collection<RainbowOfferDto> iterateRequests() {
        Collection<RainbowOfferDto> dtos = Lists.newLinkedList();
        Collection<RainbowOfferDto> singleShotOffers;
        ResponseEntity response;
        int cnt = 0;
        do {
            response = getResponse(rainbowRequest.prepareHttpEntity(cnt * rainbowRequest.getToDownload()));
            //emergency loop exit
            if (!response.getStatusCode().is2xxSuccessful()) break;
            singleShotOffers = convertToDto(response);
            dtos.addAll(singleShotOffers);
            cnt++;
        }
        while (response.getStatusCode().is2xxSuccessful() && !singleShotOffers.isEmpty());

        if (dtos.size() != 0) log.info("Successfully downloaded {} RainbowTours offers.", dtos.size());
        else log.warn("No RainbowTours offer matching given criteria downloaded!");

        return dtos;
    }

    private ResponseEntity getResponse(HttpEntity requestEntity) {
        return responseService.requestForOffers(Request.RAINBOW_TOURS.getUrl(), HttpMethod.POST, requestEntity);
    }

    private Collection<RainbowOfferDto> convertToDto(ResponseEntity response) {
        Collection<RainbowOfferClientDto> cdtos = (Collection<RainbowOfferClientDto>) parserService.parse(response);
        return cdtos.stream()
                .map(cdtoDtoConverter::toDto)
                .collect(Collectors.toList());
    }

    @Autowired
    @Qualifier("rainbowCdtoDtoConverterImpl")
    public void setCdtoDtoConverter(RainbowCdtoDtoConverter converter) {
        this.cdtoDtoConverter = converter;
    }

    @Autowired
    public void setResponseService(ResponseService responseService) {
        this.responseService = responseService;
    }

    @Autowired
    public void setParserService(ParserServiceRainbowImpl parserService) {
        this.parserService = parserService;
    }

    @Autowired
    public void setRainbowRequest(RainbowRequest rainbowRequest) {
        this.rainbowRequest = rainbowRequest;
    }

}
