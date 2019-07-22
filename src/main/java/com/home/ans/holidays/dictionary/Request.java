package com.home.ans.holidays.dictionary;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.net.URI;

@Getter
@AllArgsConstructor
public enum Request {

    RAINBOW_TOURS(URI.create("https://rpl-api.r.pl/szukaj/api/wyszukaj")),
    TUI(URI.create("https://www.tui.pl/search/offers"));

    private URI url;
}
