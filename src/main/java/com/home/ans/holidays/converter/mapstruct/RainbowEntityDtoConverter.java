package com.home.ans.holidays.converter.mapstruct;

import com.home.ans.holidays.converter.EntityDtoConverter;
import com.home.ans.holidays.entity.RainbowOfferEntity;
import com.home.ans.holidays.model.dto.RainbowOfferDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RainbowEntityDtoConverter extends EntityDtoConverter<RainbowOfferDto, RainbowOfferEntity> {

    RainbowOfferEntity toEntity(RainbowOfferDto dto);

    RainbowOfferDto toDto(RainbowOfferEntity entity);
}
