package com.home.ans.holidays.controller;

import com.home.ans.holidays.converter.mapstruct.RainbowEntityDtoConverter;
import com.home.ans.holidays.entity.RainbowOfferEntity;
import com.home.ans.holidays.model.dto.RainbowOfferDto;
import com.home.ans.holidays.repository.RainbowOfferRepository;
import com.home.ans.holidays.service.HelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/travel/rainbow")
public class RainbowClient {

    private RainbowOfferRepository rainbowOfferRepository;
    private HelperService helperService;
    private RainbowEntityDtoConverter entityDtoConverter;

    @GetMapping("/offer")
    public ResponseEntity makeCascadeRequest() {
        List<RainbowOfferDto> dtos = helperService.iterateRequests();
        List<RainbowOfferEntity> entities = dtos.stream()
                .map(entityDtoConverter::toEntity)
                .collect(Collectors.toList());
        rainbowOfferRepository.saveAll(entities);

        return ResponseEntity.ok()
                .header(HttpHeaders.DATE, LocalDateTime.now().toString())
                .body(String.format("Download finished with %d offers", dtos.size()));
    }

    @Autowired
    @Qualifier("rainbowEntityDtoConverterImpl")
    public void setEntityDtoConverter(RainbowEntityDtoConverter converter) {
        this.entityDtoConverter = converter;
    }

    @Autowired
    public void setHelperService(HelperService helperService) {
        this.helperService = helperService;
    }

    @Autowired
    public void setRainbowOfferRepository(RainbowOfferRepository rainbowOfferRepository) {
        this.rainbowOfferRepository = rainbowOfferRepository;
    }

}
