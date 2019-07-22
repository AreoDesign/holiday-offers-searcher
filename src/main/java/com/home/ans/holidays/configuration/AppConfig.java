package com.home.ans.holidays.configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.home.ans.holidays.model.cdto.RainbowOfferClientDto;
import com.home.ans.holidays.model.cdto.TuiOfferClientDto;
import com.home.ans.holidays.utils.RainbowJsonDeserializer;
import com.home.ans.holidays.utils.TuiJsonDeserializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

@Configuration
public class AppConfig {

    @Bean
    @Qualifier("restTemplate")
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofMillis(30000))
                .setReadTimeout(Duration.ofMillis(30000))
                .build();
    }

    @Bean
    @Qualifier("gson")
    public Gson gson() {
        return new GsonBuilder()
                .registerTypeAdapter(RainbowOfferClientDto.class, new RainbowJsonDeserializer())
                .registerTypeAdapter(TuiOfferClientDto.class, new TuiJsonDeserializer())
                .create();
    }

    @Bean
    @Qualifier("dateFormatter")
    public DateTimeFormatter dateFormatter() {
        return DateTimeFormatter.ISO_LOCAL_DATE;
    }

    @Bean
    @Qualifier("dateTimeFormatter")
    public DateTimeFormatter dateTimeFormatter() {
        return DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    }

    @Bean
    @Qualifier("headerDateTypeConverter")
    public Function headerDateTypeConverter() {
        Function<ResponseEntity, LocalDateTime> converter = (response ->
                LocalDateTime.ofInstant(Instant.ofEpochMilli(response.getHeaders().getDate()), ZoneId.of("GMT+2")));
        return converter;
    }

}
