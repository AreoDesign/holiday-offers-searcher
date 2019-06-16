package com.home.ans.holidays.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Paginacja {
    private String przeczytane;
    private String iloscDoPobrania;
}
