package com.home.ans.holidays.service.impl;

import com.google.common.collect.Lists;
import com.home.ans.holidays.component.TravelRequest;
import com.home.ans.holidays.converter.mapstruct.RainbowCdtoDtoConverter;
import com.home.ans.holidays.dictionary.Request;
import com.home.ans.holidays.model.dto.RainbowOfferDto;
import com.home.ans.holidays.service.HelperService;
import com.home.ans.holidays.service.ParserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class HelperServiceImpl implements HelperService {

    private TravelRequest travelRequest;
    private ParserService parserService;
    private ResponseServiceImpl responseService;
    private RainbowCdtoDtoConverter cdtoDtoConverter;

    public List<RainbowOfferDto> iterateRequests() {
        List<RainbowOfferDto> dtos = Lists.newLinkedList();
        List<RainbowOfferDto> singleShotOffers;
        ResponseEntity response;
        int cnt = 0;
        do {
            response = this.getResponse(travelRequest.prepareHttpEntity(cnt * travelRequest.getToDownload()));
            singleShotOffers = convertToDto(response);
            dtos.addAll(singleShotOffers);
            cnt++;
        }
        while (response.getStatusCode().is2xxSuccessful() && !singleShotOffers.isEmpty());

        if (dtos.size() != 0) log.info("Successfully downloaded {} offers.", dtos.size());
        else log.warn("No offer matching given criteria downloaded!");

        return dtos;
    }

    private ResponseEntity getResponse(HttpEntity requestEntity) {
        return responseService.requestForOffers(Request.RAINBOW_TOURS.getUrl(), requestEntity);
    }

    private List<RainbowOfferDto> convertToDto(ResponseEntity response) {
        return parserService.parse(response).stream()
                .map(cdtoDtoConverter::toDto)
                .collect(Collectors.toList());
    }

    @Autowired
    @Qualifier("rainbowCdtoDtoConverterImpl")
    public void setCdtoDtoConverter(RainbowCdtoDtoConverter converter) {
        this.cdtoDtoConverter = converter;
    }

    @Autowired
    public void setResponseService(ResponseServiceImpl responseService) {
        this.responseService = responseService;
    }

    @Autowired
    public void setParserService(ParserService parserService) {
        this.parserService = parserService;
    }

    @Autowired
    public void setTravelRequest(TravelRequest travelRequest) {
        this.travelRequest = travelRequest;
    }

}
