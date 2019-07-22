package com.home.ans.holidays.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.home.ans.holidays.model.cdto.TuiOfferClientDto;
import com.home.ans.holidays.service.ParserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class ParserServiceTuiImpl implements ParserService {

    private static final String KEY_FOR_OFFERS = "offers";
    private Function<ResponseEntity, LocalDateTime> headerDateTypeConverter;
    private Gson gson;
    private Type tuiType = new TypeToken<ArrayList<TuiOfferClientDto>>() {
    }.getType();

    private Function<String, Collection<TuiOfferClientDto>> offersParser = (responseBody -> {
        Map responseBodyMap = gson.fromJson(responseBody, Map.class);
        String jsonWithOffers = gson.toJson(responseBodyMap.get(KEY_FOR_OFFERS));
        return gson.fromJson(jsonWithOffers, tuiType);
    });

    @Override
    public Collection<TuiOfferClientDto> parse(ResponseEntity response) {
        String responseBody = (String) response.getBody();
        Collection<TuiOfferClientDto> cdtos = offersParser.apply(responseBody);
        cdtos.forEach(cdto -> cdto.setRequestDate(headerDateTypeConverter.apply(response)));

        return cdtos;
    }

    @Autowired
    @Qualifier("gson")
    public void setGson(Gson gson) {
        this.gson = gson;
    }

    @Autowired
    @Qualifier("headerDateTypeConverter")
    public void setHeaderDateTypeConverter(Function<ResponseEntity, LocalDateTime> headerDateTypeConverter) {
        this.headerDateTypeConverter = headerDateTypeConverter;
    }
}
