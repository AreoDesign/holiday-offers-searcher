package com.home.ans.holidays.converter;

public interface CdtoDtoConverter<K, V> {

    K toDto(K clientDto);

    V toClientDto(K dto);
}
