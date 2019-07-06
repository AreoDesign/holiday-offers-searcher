package com.home.ans.holidays.converter;

public interface EntityDtoConverter<K, V> {

    K toDto(V clientDto);

    V toEntity(K dto);
}
