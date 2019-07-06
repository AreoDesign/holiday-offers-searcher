package com.home.ans.holidays.controller;

import com.home.ans.holidays.component.TravelRequest;
import com.home.ans.holidays.converter.mapstruct.RainbowCdtoDtoConverter;
import com.home.ans.holidays.converter.mapstruct.RainbowEntityDtoConverter;
import com.home.ans.holidays.dictionary.Request;
import com.home.ans.holidays.entity.RainbowOfferEntity;
import com.home.ans.holidays.model.dto.RainbowOfferDto;
import com.home.ans.holidays.repository.RainbowOfferRepository;
import com.home.ans.holidays.service.ParserService;
import com.home.ans.holidays.service.impl.RainbowServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/travel")
public class RainbowClient {

    private RainbowServiceImpl rainbowService;
    private TravelRequest travelRequest;
    private ParserService parserService;
    private RainbowEntityDtoConverter entityDtoConverter;
    private RainbowCdtoDtoConverter cdtoDtoConverter;
    private RainbowOfferRepository rainbowOfferRepository;

    @GetMapping("/single-shot-request")
    public List<RainbowOfferDto> singleShotRequest() {
        ResponseEntity response = this.getDefaultResponse();
        List<RainbowOfferDto> dtos = parserService.parse(response).stream()
                .map(cdtoDtoConverter::toDto)
                .collect(Collectors.toList());
        List<RainbowOfferEntity> entities = dtos.stream().map(entityDtoConverter::toEntity).collect(Collectors.toList());
        rainbowOfferRepository.saveAll(entities);

        return dtos;
    }

    @GetMapping("/default")
    public ResponseEntity getDefaultResponse() {
        return rainbowService.requestForOffers(Request.RAINBOW_TOURS.getUrl(), travelRequest.prepareHttpEntity());
    }

    @GetMapping("/custom")
    public ResponseEntity getCustomResponse(HttpEntity requestEntity) {
        return rainbowService.requestForOffers(Request.RAINBOW_TOURS.getUrl(), requestEntity);
    }

    @Autowired
    public void setRainbowService(RainbowServiceImpl rainbowService) {
        this.rainbowService = rainbowService;
    }

    @Autowired
    public void setTravelRequest(TravelRequest travelRequest) {
        this.travelRequest = travelRequest;
    }

    @Autowired
    public void setParserService(ParserService parserService) {
        this.parserService = parserService;
    }

    @Autowired
    @Qualifier("rainbowEntityDtoConverterImpl")
    public void setEntityDtoConverter(RainbowEntityDtoConverter converter) {
        this.entityDtoConverter = converter;
    }

    @Autowired
    @Qualifier("rainbowCdtoDtoConverterImpl")
    public void setCdtoDtoConverter(RainbowCdtoDtoConverter converter) {
        this.cdtoDtoConverter = converter;
    }

    @Autowired
    public void setRainbowOfferRepository(RainbowOfferRepository rainbowOfferRepository) {
        this.rainbowOfferRepository = rainbowOfferRepository;
    }

}
