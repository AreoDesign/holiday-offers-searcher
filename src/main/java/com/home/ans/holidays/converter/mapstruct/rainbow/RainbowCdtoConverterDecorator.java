package com.home.ans.holidays.converter.mapstruct.rainbow;

import com.home.ans.holidays.model.cdto.RainbowOfferClientDto;
import com.home.ans.holidays.model.dto.RainbowOfferDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class RainbowCdtoConverterDecorator implements RainbowCdtoDtoConverter {

    public static final String RAINBOW_PREFIX = "https://r.pl";

    private RainbowCdtoDtoConverter delegate;

    @Override
    public RainbowOfferDto toDto(RainbowOfferClientDto clientDto) {
        RainbowOfferDto dto = delegate.toDto(clientDto);
        dto.setOfertaUrl(RAINBOW_PREFIX + clientDto.getOfertaUrl());
        return dto;
    }

    @Override
    public RainbowOfferClientDto toClientDto(RainbowOfferDto dto) {
        RainbowOfferClientDto cdto = delegate.toClientDto(dto);
        cdto.setOfertaUrl(dto.getOfertaUrl().replace(RAINBOW_PREFIX, StringUtils.EMPTY));
        return cdto;
    }

    @Autowired
    @Qualifier(value = "delegate")
    public void setDelegate(RainbowCdtoDtoConverter delegate) {
        this.delegate = delegate;
    }
}
