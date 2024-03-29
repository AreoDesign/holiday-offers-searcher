package com.home.ans.holidays.controller;

import com.google.common.collect.ImmutableSet;
import com.home.ans.holidays.converter.mapstruct.tui.TuiEntityDtoConverter;
import com.home.ans.holidays.entity.TuiOfferEntity;
import com.home.ans.holidays.model.dto.TuiOfferDto;
import com.home.ans.holidays.repository.TuiOfferRepository;
import com.home.ans.holidays.service.NotificationService;
import com.home.ans.holidays.service.RequestIteratorService;
import com.home.ans.holidays.service.impl.RequestIteratorServiceTuiImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.home.ans.holidays.component.TuiRequest.BoardType.ALL_INCLUSIVE;
import static com.home.ans.holidays.component.TuiRequest.BoardType.FULL_BOARD;
import static com.home.ans.holidays.component.TuiRequest.BoardType.getCodesForBoardTypes;

@RestController
@RequestMapping("/travel/tui")
@Slf4j
public class TuiClient {

    private RequestIteratorService requestIteratorService;
    private TuiEntityDtoConverter entityDtoConverter;
    private TuiOfferRepository offerRepository;
    private NotificationService notificationService;

    private Predicate<? super TuiOfferEntity> notificationCriterion = offer -> offer.getDestination().contains("Grecja") &&
            offer.getDuration().compareTo(6) > 0 &&
            getCodesForBoardTypes(ImmutableSet.of(ALL_INCLUSIVE, FULL_BOARD)).contains(offer.getBoardType()) &&
            offer.getDepartureDateAndTime().isAfter(LocalDateTime.of(2019, 9, 1, 0, 0)) &&
            offer.getDepartureDateAndTime().isBefore(LocalDateTime.of(2019, 10, 31, 24, 59)) &&
            ((offer.getHotelStandard() > 4d && offer.getDiscountPerPersonPrice() < 1500) ||
                    (offer.getHotelStandard() > 3d && offer.getDiscountPerPersonPrice() < 1200));

    @GetMapping("/offer")
    public ResponseEntity makeCascadeRequest() {
        List<TuiOfferDto> dtos = (List<TuiOfferDto>) requestIteratorService.iterateRequests();
        List<TuiOfferEntity> entities = dtos.stream()
                .map(entityDtoConverter::toEntity)
                .collect(Collectors.toList());
        entities = offerRepository.saveAll(entities);

        entities.stream().filter(notificationCriterion).forEach(offer -> notificationService.notifyAboutTuiOffer(offer));

        return ResponseEntity.ok()
                .header(HttpHeaders.DATE, LocalDateTime.now().toString())
                .body(String.format("Download finished with %d offers", dtos.size()));
    }

    @Autowired
    public void setRequestIteratorService(RequestIteratorServiceTuiImpl requestIteratorService) {
        this.requestIteratorService = requestIteratorService;
    }

    @Autowired
    public void setEntityDtoConverter(TuiEntityDtoConverter entityDtoConverter) {
        this.entityDtoConverter = entityDtoConverter;
    }

    @Autowired
    public void setOfferRepository(TuiOfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @Autowired
    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
}
