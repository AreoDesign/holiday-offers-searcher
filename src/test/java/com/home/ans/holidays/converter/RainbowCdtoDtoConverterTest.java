package com.home.ans.holidays.converter;

import com.home.ans.holidays.converter.mapstruct.RainbowCdtoConverterDecorator;
import com.home.ans.holidays.converter.mapstruct.RainbowCdtoDtoConverter;
import com.home.ans.holidays.converter.mapstruct.RainbowCdtoDtoConverterImpl;
import com.home.ans.holidays.converter.mapstruct.RainbowCdtoDtoConverterImpl_;
import com.home.ans.holidays.model.cdto.RainbowOfferClientDto;
import com.home.ans.holidays.model.dto.RainbowOfferDto;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.home.ans.holidays.converter.mapstruct.RainbowCdtoConverterDecorator.RAINBOW_PREFIX;
import static com.home.ans.holidays.converter.mapstruct.RainbowCdtoDtoConverter.RAINBOW_DATE_TIME_FORMAT;
import static com.home.ans.holidays.service.impl.ParserServiceImplTest.prepareCdto;
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
        assertThat(clientDto.getDataZapytania()).isEqualTo(dto.getDataZapytania());
        assertThat(clientDto.getOfertaId()).isEqualTo(dto.getOfertaId());
        assertThat(RAINBOW_PREFIX + clientDto.getOfertaUrl()).isEqualTo(dto.getOfertaUrl());
        assertThat(stringToLocalDate(clientDto.getDataWKodzieProduktu())).isEqualTo(dto.getDataWKodzieProduktu());
        assertThat(Double.valueOf(clientDto.getOcenaOgolna())).isEqualTo(dto.getOcenaOgolna());
        assertThat(clientDto.getCenaPrzedPromocja()).isEqualTo(dto.getCenaPrzedPromocja());
        assertThat(clientDto.getCenaAktualna()).isEqualTo(dto.getCenaAktualna());
        assertThat(clientDto.getLiczbaDni()).isEqualTo(dto.getLiczbaDni());
        assertThat(clientDto.getProcentPromocji()).isEqualTo(dto.getProcentPromocji());
        assertThat(clientDto.getHotelId()).isEqualTo(dto.getHotelId());
        assertThat(clientDto.getNazwaHotelu()).isEqualTo(dto.getNazwaHotelu());
        assertThat(clientDto.getGwiazdkiHotelu()).isEqualTo(dto.getGwiazdkiHotelu());
        assertThat(clientDto.getLokalizacja()).isEqualTo(dto.getLokalizacja());

    }

    private LocalDate stringToLocalDate(String dataWKodzieProduktu) {
        return LocalDate.parse(dataWKodzieProduktu, DateTimeFormatter.ofPattern(RAINBOW_DATE_TIME_FORMAT));
    }

}