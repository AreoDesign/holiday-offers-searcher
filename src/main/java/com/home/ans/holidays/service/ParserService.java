package com.home.ans.holidays.service;

import com.home.ans.holidays.model.cdto.ClientDto;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

public interface ParserService {
    Collection<? extends ClientDto> parse(ResponseEntity response);
}
