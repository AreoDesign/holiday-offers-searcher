package com.home.ans.holidays.configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.home.ans.holidays.model.cdto.RainbowOfferClientDto;
import com.home.ans.holidays.utils.RainbowJsonDeserializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.format.DateTimeFormatter;

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
                .create();
    }

    @Bean
    @Qualifier("dateTimeFormatter")
    public DateTimeFormatter dateTimeFormatter() {
        return DateTimeFormatter.ISO_LOCAL_DATE;
    }

}
