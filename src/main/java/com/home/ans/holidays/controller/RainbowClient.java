package com.home.ans.holidays.controller;

import com.home.ans.holidays.converter.mapstruct.RainbowEntityDtoConverter;
import com.home.ans.holidays.entity.RainbowOfferEntity;
import com.home.ans.holidays.model.dto.RainbowOfferDto;
import com.home.ans.holidays.presentation.RainbowDataImporter;
import com.home.ans.holidays.repository.RainbowOfferRepository;
import com.home.ans.holidays.service.RequestIteratorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/travel/rainbow")
@Slf4j
public class RainbowClient {

    private RainbowOfferRepository rainbowOfferRepository;
    private RequestIteratorService requestIteratorService;
    private RainbowEntityDtoConverter entityDtoConverter;
    private RainbowDataImporter rainbowDataImporter;

    @GetMapping("/offer")
    public ResponseEntity makeCascadeRequest() {
        List<RainbowOfferDto> dtos = requestIteratorService.iterateRequests();
        List<RainbowOfferEntity> entities = dtos.stream()
                .map(entityDtoConverter::toEntity)
                .collect(Collectors.toList());
        rainbowOfferRepository.saveAll(entities);

        return ResponseEntity.ok()
                .header(HttpHeaders.DATE, LocalDateTime.now().toString())
                .body(String.format("Download finished with %d offers", dtos.size()));
    }

    @GetMapping("/offer/summary")
    public HttpStatus importToExcel() {
        try {
            rainbowDataImporter.toXls();
            return HttpStatus.OK;
        } catch (IOException e) {
            log.error("importing offers from db to excel failed!");
            e.printStackTrace();
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @Autowired
    @Qualifier("rainbowEntityDtoConverterImpl")
    public void setEntityDtoConverter(RainbowEntityDtoConverter converter) {
        this.entityDtoConverter = converter;
    }

    @Autowired
    public void setRequestIteratorService(RequestIteratorService requestIteratorService) {
        this.requestIteratorService = requestIteratorService;
    }

    @Autowired
    public void setRainbowOfferRepository(RainbowOfferRepository rainbowOfferRepository) {
        this.rainbowOfferRepository = rainbowOfferRepository;
    }

    @Autowired
    public void setRainbowDataImporter(RainbowDataImporter rainbowDataImporter) {
        this.rainbowDataImporter = rainbowDataImporter;
    }
}
