package com.home.ans.holidays.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class Konfiguracja {
    private List<String> wiek;
    private String liczbaPokoi;
}
