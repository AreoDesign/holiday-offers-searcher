package com.home.ans.holidays.converter.mapstruct.tui;

import com.home.ans.holidays.model.cdto.TuiOfferClientDto;
import com.home.ans.holidays.model.dto.TuiOfferDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class TuiCdtoConverterDecorator implements TuiCdtoDtoConverter {

    private static final String TUI_PREFIX = "https://www.tui.pl";

    private TuiCdtoDtoConverter delegate;

    @Override
    public TuiOfferDto toDto(TuiOfferClientDto clientDto) {
        TuiOfferDto dto = delegate.toDto(clientDto);
        dto.setOfferUrl(TUI_PREFIX + clientDto.getOfferUrl());
        return dto;
    }

    @Override
    public TuiOfferClientDto toClientDto(TuiOfferDto dto) {
        TuiOfferClientDto cdto = delegate.toClientDto(dto);
        cdto.setOfferUrl(dto.getOfferUrl().replace(TUI_PREFIX, StringUtils.EMPTY));
        return cdto;
    }

    @Autowired
    @Qualifier(value = "delegate")
    public void setDelegate(TuiCdtoDtoConverter delegate) {
        this.delegate = delegate;
    }
}
