package com.home.ans.holidays.configuration;

import com.home.ans.holidays.controller.RainbowClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class SpringConfig {

    private RainbowClient rainbowClient;

    @Scheduled(cron = "0 0 9,12,15,18,21 * * ?")
    public void requestForTravelOffer() {
        rainbowClient.makeCascadeRequest();
    }

    @Autowired
    public void setRainbowClient(RainbowClient rainbowClient) {
        this.rainbowClient = rainbowClient;
    }
}
