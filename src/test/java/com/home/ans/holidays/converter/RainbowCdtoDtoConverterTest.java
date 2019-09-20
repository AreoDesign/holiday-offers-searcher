package com.home.ans.holidays.converter;

import com.home.ans.holidays.converter.mapstruct.rainbow.RainbowCdtoConverterDecorator;
import com.home.ans.holidays.converter.mapstruct.rainbow.RainbowCdtoDtoConverter;
import com.home.ans.holidays.converter.mapstruct.rainbow.RainbowCdtoDtoConverterImpl;
import com.home.ans.holidays.converter.mapstruct.rainbow.RainbowCdtoDtoConverterImpl_;
import com.home.ans.holidays.model.cdto.RainbowOfferClientDto;
import com.home.ans.holidays.model.dto.RainbowOfferDto;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.home.ans.holidays.converter.mapstruct.rainbow.RainbowCdtoConverterDecorator.RAINBOW_PREFIX;
import static com.home.ans.holidays.converter.mapstruct.rainbow.RainbowCdtoDtoConverter.RAINBOW_DATE_TIME_FORMAT;
import static com.home.ans.holidays.service.impl.ParserServiceRainbowImplTest.prepareCdto;
import static org.assertj.core.api.Assertions.assertThat;

public class RainbowCdtoDtoConverterTest {

    private RainbowCdtoDtoConverter converter_ = new RainbowCdtoDtoConverterImpl_();
    private RainbowCdtoConverterDecorator converter = new RainbowCdtoDtoConverterImpl();

    @Before
    public void setup() {
        converter.setDelegate(converter_);
    }

    @Test
    public void givenCdto_whenConvertToDto_thenCorrectDto() {
//        given
        RainbowOfferClientDto clientDto = prepareCdto();
//        when
        RainbowOfferDto dto = converter.toDto(clientDto);
//        then
        assertThat(clientDto.getRequestDate()).isEqualTo(dto.getRequestDate());
        assertThat(clientDto.getOfferCode()).isEqualTo(dto.getOfferCode());
        assertThat(RAINBOW_PREFIX + clientDto.getOfferUrl()).isEqualTo(dto.getOfferUrl());
        assertThat(stringToLocalDate(clientDto.getDepartureDateAndTime())).isEqualTo(dto.getDepartureDateAndTime());
        assertThat(clientDto.getBoardType()).isEqualTo(dto.getBoardType());
        assertThat(Double.valueOf(clientDto.getRating())).isEqualTo(dto.getRating());
        assertThat(clientDto.getOriginalPerPersonPrice()).isEqualTo(dto.getOriginalPerPersonPrice());
        assertThat(clientDto.getDiscountPerPersonPrice()).isEqualTo(dto.getDiscountPerPersonPrice());
        assertThat(clientDto.getDuration()).isEqualTo(dto.getDuration());
        assertThat(clientDto.getPromotionPercentage()).isEqualTo(dto.getPromotionPercentage());
        assertThat(clientDto.getHotelCode()).isEqualTo(dto.getHotelCode());
        assertThat(clientDto.getHotelName()).isEqualTo(dto.getHotelName());
        assertThat(clientDto.getHotelStandard()).isEqualTo(dto.getHotelStandard());
        assertThat(clientDto.getDestination()).isEqualTo(dto.getDestination());

    }

    private LocalDate stringToLocalDate(String dataWKodzieProduktu) {
        return LocalDate.parse(dataWKodzieProduktu, DateTimeFormatter.ofPattern(RAINBOW_DATE_TIME_FORMAT));
    }

}