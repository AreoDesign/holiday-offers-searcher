package com.home.ans.holidays.presentation;

import com.home.ans.holidays.entity.RainbowOfferEntity;
import com.home.ans.holidays.repository.RainbowOfferRepository;
import org.jxls.template.SimpleExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

@Service
public class RainbowDataImporter {

    private RainbowOfferRepository rainbowOfferRepository;

    public void toXls() throws IOException {
        List<RainbowOfferEntity> offers = rainbowOfferRepository.findAll();
        List<String> headers = Arrays.asList("lokalizacja", "nazwa hotelu", "cena aktualna", "wyżywienie", "gwiazdki hotelu", "liczba dni", "data wyjazdu", "ocena ogólna", "data zapytania");
        String props = "lokalizacja, nazwaHotelu, cenaAktualna, wyzywienie, gwiazdkiHotelu, liczbaDni, dataWKodzieProduktu, ocenaOgolna, dataZapytania";
        try (OutputStream os = new FileOutputStream("target/rainbow_offers_summary.xls")) {
            SimpleExporter exporter = new SimpleExporter();
            exporter.gridExport(headers, offers, props, os);
        }
    }

    @Autowired
    public void setRainbowOfferRepository(RainbowOfferRepository rainbowOfferRepository) {
        this.rainbowOfferRepository = rainbowOfferRepository;
    }
}
