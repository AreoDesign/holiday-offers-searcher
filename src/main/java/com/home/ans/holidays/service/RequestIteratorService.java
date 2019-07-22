package com.home.ans.holidays.service;

import com.home.ans.holidays.model.dto.OfferDto;

import java.util.List;

public interface RequestIteratorService {

    List<? extends OfferDto> iterateRequests();

}
