package com.home.ans.holidays.service;

import com.home.ans.holidays.model.dto.OfferDto;

import java.util.Collection;

public interface RequestIteratorService {

    Collection<? extends OfferDto> iterateRequests();

}
