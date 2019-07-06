package com.home.ans.holidays.converter;

public interface CdtoDtoConverter<K, V> {

    K toDto(V clientDto);

    V toClientDto(K dto);
}
