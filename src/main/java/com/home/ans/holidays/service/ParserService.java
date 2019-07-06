package com.home.ans.holidays.service;

import com.home.ans.holidays.model.cdto.RainbowOfferClientDto;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

public interface ParserService {
    Collection<RainbowOfferClientDto> parse(ResponseEntity response);
}
