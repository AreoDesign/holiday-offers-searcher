package com.home.ans.holidays.converter.mapstruct;

import com.home.ans.holidays.converter.CdtoDtoConverter;
import com.home.ans.holidays.model.cdto.RainbowOfferClientDto;
import com.home.ans.holidays.model.dto.RainbowOfferDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface RainbowCdtoDtoConverter extends CdtoDtoConverter<RainbowOfferDto, RainbowOfferClientDto> {

    @Mappings({
            @Mapping(target = "dataWKodzieProduktu", source = "clientDto.dataWKodzieProduktu", dateFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'"),
            @Mapping(target = "ocenaOgolna", source = "clientDto.ocenaOgolna", numberFormat = "#.##E0"),
    })
    RainbowOfferDto toDto(RainbowOfferClientDto clientDto);

    @InheritInverseConfiguration
    RainbowOfferClientDto toClientDto(RainbowOfferDto dto);

}
