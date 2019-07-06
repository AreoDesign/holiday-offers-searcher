package com.home.ans.holidays.converter;

import com.home.ans.holidays.converter.mapstruct.RainbowCdtoDtoConverter;
import com.home.ans.holidays.converter.mapstruct.RainbowCdtoDtoConverterImpl;
import com.home.ans.holidays.model.cdto.RainbowOfferClientDto;
import com.home.ans.holidays.model.dto.RainbowOfferDto;
import com.home.ans.holidays.service.impl.ParserServiceImplTest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class RainbowCdtoDtoConverterTest {

    @InjectMocks
    private RainbowCdtoDtoConverter converter = new RainbowCdtoDtoConverterImpl();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void given_whenMaps_thenCorrect() {
//        given
        RainbowOfferClientDto clientDto = ParserServiceImplTest.prepareCdto();
//        when
        RainbowOfferDto dto = converter.toDto(clientDto);
//        then
//        assertThat(clientDto.getDataWKodzieProduktu()).isEqualTo(dto.getDataWKodzieProduktu());

    }

}