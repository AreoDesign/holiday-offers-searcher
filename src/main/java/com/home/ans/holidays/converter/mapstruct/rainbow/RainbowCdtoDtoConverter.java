package com.home.ans.holidays.converter.mapstruct.rainbow;

import com.home.ans.holidays.converter.CdtoDtoConverter;
import com.home.ans.holidays.model.cdto.RainbowOfferClientDto;
import com.home.ans.holidays.model.dto.RainbowOfferDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
@DecoratedWith(RainbowCdtoConverterDecorator.class)
public interface RainbowCdtoDtoConverter extends CdtoDtoConverter<RainbowOfferDto, RainbowOfferClientDto> {

    String RAINBOW_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    @Mappings({
            @Mapping(target = "dataWKodzieProduktu", source = "clientDto.dataWKodzieProduktu", dateFormat = RAINBOW_DATE_TIME_FORMAT)
    })
    RainbowOfferDto toDto(RainbowOfferClientDto clientDto);

    @InheritInverseConfiguration
    RainbowOfferClientDto toClientDto(RainbowOfferDto dto);

}
