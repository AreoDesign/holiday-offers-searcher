package com.home.ans.holidays.presentation;

import com.home.ans.holidays.entity.OfferEntity;
import com.home.ans.holidays.repository.OfferRepository;
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

    private OfferRepository offerRepository;

    public void toXls() throws IOException {
        List<OfferEntity> offers = offerRepository.findAll();
        List<String> headers = Arrays.asList("lokalizacja", "nazwa hotelu", "cena aktualna", "wyżywienie", "gwiazdki hotelu", "liczba dni pobytu", "data wyjazdu", "ocena ogólna", "data zapytania");
        String props = "destination, hotelName, cenaAktualna, boardType, hotelStandard, duration, departureDateAndTime, rating, requestDate";
        try (OutputStream os = new FileOutputStream("target/offers_summary.xls")) {
            SimpleExporter exporter = new SimpleExporter();
            exporter.gridExport(headers, offers, props, os);
        }
    }

    @Autowired
    public void setOfferRepository(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }
}
