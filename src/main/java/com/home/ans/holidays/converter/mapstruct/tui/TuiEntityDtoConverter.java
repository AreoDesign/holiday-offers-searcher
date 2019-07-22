package com.home.ans.holidays.converter.mapstruct.tui;

import com.home.ans.holidays.converter.EntityDtoConverter;
import com.home.ans.holidays.entity.TuiOfferEntity;
import com.home.ans.holidays.model.dto.TuiOfferDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TuiEntityDtoConverter extends EntityDtoConverter<TuiOfferDto, TuiOfferEntity> {

    TuiOfferEntity toEntity(TuiOfferDto dto);

    TuiOfferDto toDto(TuiOfferEntity entity);
}
