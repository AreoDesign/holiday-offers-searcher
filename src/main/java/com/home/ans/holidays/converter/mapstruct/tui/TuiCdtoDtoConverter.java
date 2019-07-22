package com.home.ans.holidays.converter.mapstruct.tui;

import com.home.ans.holidays.converter.CdtoDtoConverter;
import com.home.ans.holidays.model.cdto.TuiOfferClientDto;
import com.home.ans.holidays.model.dto.TuiOfferDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
@DecoratedWith(TuiCdtoConverterDecorator.class)
public interface TuiCdtoDtoConverter extends CdtoDtoConverter<TuiOfferDto, TuiOfferClientDto> {

    String CUSTOM_TUI_DATE_TIME_FORMAT = "dd.MM.yyyy'T'HH:mm";

    @Mappings({
            @Mapping(target = "departureDateAndTime", source = "clientDto.departureDateAndTime", dateFormat = CUSTOM_TUI_DATE_TIME_FORMAT)
    })
    TuiOfferDto toDto(TuiOfferClientDto clientDto);

    @InheritInverseConfiguration
    TuiOfferClientDto toClientDto(TuiOfferDto dto);

}
