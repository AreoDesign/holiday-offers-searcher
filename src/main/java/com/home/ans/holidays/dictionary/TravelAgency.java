package com.home.ans.holidays.dictionary;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TravelAgency {
    TUI("TUI"),
    RAINBOW_TOURS("RAINBOW");

    private String source;
}
