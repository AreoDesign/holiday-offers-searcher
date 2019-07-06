package com.home.ans.holidays.service.impl;

import com.google.gson.Gson;
import com.home.ans.holidays.model.cdto.RainbowOfferClientDto;
import com.home.ans.holidays.service.ParserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ParserServiceImplTest {

    private Gson gson;
    private ParserService parserService;

    @Test
    public void convertJsonToCDtoReturnsCorrect() {
//        given
        RainbowOfferClientDto clientDto = prepareCdto();
        ResponseEntity<String> responseEntity = ResponseEntity.ok()
                .header("Date", "Tue, 02 Jul 2019 18:25:55 GMT")
                .body(prepareResponseBodyJson());
//        when
        Collection<RainbowOfferClientDto> cdtos = parserService.parse(responseEntity);
//        then
        assertThat(cdtos).contains(clientDto);
    }

    public static RainbowOfferClientDto prepareCdto() {
        return RainbowOfferClientDto.builder()
                .dataZapytania(LocalDateTime.parse("Tue, 02 Jul 2019 18:25:55 GMT", DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH)))
                .ofertaId("zante-village_89776")
                .ofertaUrl("/zakynthos-wczasy/zante-village?data=20190924&liczbaDoroslych=2&liczbaDzieci=1&liczbaPokoi=1&wiekDzieci=0&miastoWyjazdu=warszawa&wyzywienie=all-inclusive")
                .dataWKodzieProduktu("2019-09-24T00:00:00Z")
                .ocenaOgolna("4.60")
                .cenaPrzedPromocja(1550)
                .cenaAktualna(1550)
                .liczbaDni(4)
                .procentPromocji(38)
                .hotelId(1245)
                .nazwaHotelu("Zante Village")
                .gwiazdkiHotelu(3.5d)
                .lokalizacja("Grecja/Zakynthos")
                .build();
    }

    private String prepareResponseBodyJson() {
        return "{\n" +
                "  \"CzyPominieteDaty\": false,\n" +
                "  \"CzyPominieteMiastaWyjazdu\": false,\n" +
                "  \"CzyBrakKonfiguracjiDlaPromocji\": false,\n" +
                "  \"DaneZFiltrow\": {\n" +
                "    \"Sortowanie\": {\n" +
                "      \"CzyPoDacie\": false,\n" +
                "      \"CzyPoCenie\": true,\n" +
                "      \"CzyPoOcenach\": false,\n" +
                "      \"CzyPoPolecanych\": false,\n" +
                "      \"CzyDesc\": false\n" +
                "    }\n" +
                "  },\n" +
                "  \"Bloczki\": [\n" +
                "    {\n" +
                "      \"ID\": \"zante-village_89776\",\n" +
                "      \"KodProduktu\": \"ZTX\",\n" +
                "      \"TypWycieczkiWWW\": \"wypoczynek\",\n" +
                "      \"Zdjecie\": {\n" +
                "        \"ZdjecieUrl\": \"//images.r.pl/zdjecia/hotele/glob/1245/zante-village_grecja_zakynthos_1245_101716_149061_410x225.jpg\"\n" +
                "      },\n" +
                "      \"OfertaUrl\": \"/zakynthos-wczasy/zante-village?data=20190924&liczbaDoroslych=2&liczbaDzieci=1&liczbaPokoi=1&wiekDzieci=0&miastoWyjazdu=warszawa&wyzywienie=all-inclusive\",\n" +
                "      \"OfertaUrlDlaGoogla\": \"/zakynthos-wczasy/zante-village\",\n" +
                "      \"DataWKodzieProduktu\": \"2019-09-24T00:00:00Z\",\n" +
                "      \"MiastaWyjazdu\": [\n" +
                "        {\n" +
                "          \"Nazwa\": \"Gdańsk\",\n" +
                "          \"NazwaUrl\": \"gdansk\",\n" +
                "          \"CzyWybrane\": false\n" +
                "        },\n" +
                "        {\n" +
                "          \"Nazwa\": \"Katowice\",\n" +
                "          \"NazwaUrl\": \"katowice\",\n" +
                "          \"CzyWybrane\": false\n" +
                "        },\n" +
                "        {\n" +
                "          \"Nazwa\": \"Poznań\",\n" +
                "          \"NazwaUrl\": \"poznan\",\n" +
                "          \"CzyWybrane\": false\n" +
                "        },\n" +
                "        {\n" +
                "          \"Nazwa\": \"Warszawa\",\n" +
                "          \"NazwaUrl\": \"warszawa\",\n" +
                "          \"CzyWybrane\": true\n" +
                "        },\n" +
                "        {\n" +
                "          \"Nazwa\": \"Wrocław\",\n" +
                "          \"NazwaUrl\": \"wroclaw\",\n" +
                "          \"CzyWybrane\": false\n" +
                "        }\n" +
                "      ],\n" +
                "      \"Wyzywienia\": [\n" +
                "        {\n" +
                "          \"Nazwa\": \"All inclusive\",\n" +
                "          \"NazwaUrl\": \"all-inclusive\",\n" +
                "          \"CzyPodstawowe\": true\n" +
                "        }\n" +
                "      ],\n" +
                "      \"AtrybutyHoteli\": [\n" +
                "        {\n" +
                "          \"NazwaAtrybutu\": \"Rodzinny\",\n" +
                "          \"NazwaUrlAtrybutu\": \"rodzinny\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"NazwaAtrybutu\": \"Międzynarodowe animacje\",\n" +
                "          \"NazwaUrlAtrybutu\": \"miedzynarodowe-animacje\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"NazwaAtrybutu\": \"W/blisko centrum\",\n" +
                "          \"NazwaUrlAtrybutu\": \"wblisko-centrum\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"NazwaAtrybutu\": \"Wi-Fi\",\n" +
                "          \"NazwaUrlAtrybutu\": \"wi-fi\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"AtrybutyProduktow\": [\n" +
                "        {\n" +
                "          \"NazwaAtrybutu\": \"Bez paszportu\",\n" +
                "          \"NazwaUrlAtrybutu\": \"paszport\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"NazwaAtrybutu\": \"Bez wizy\",\n" +
                "          \"NazwaUrlAtrybutu\": \"wiza\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"NazwaAtrybutu\": \"City Break\",\n" +
                "          \"NazwaUrlAtrybutu\": \"city-break\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"Opinie\": {\n" +
                "        \"OcenaOgolna\": \"4.60\",\n" +
                "        \"LiczbaOpinii\": 164,\n" +
                "        \"CzyPokazywac\": true\n" +
                "      },\n" +
                "      \"Ceny\": [\n" +
                "        {\n" +
                "          \"PakietId\": \"89776_2263300_\",\n" +
                "          \"HotelWCId\": 2263300,\n" +
                "          \"CenaPrzedPromocja\": 1550,\n" +
                "          \"CenaAktualna\": 1550,\n" +
                "          \"LiczbaDni\": 4,\n" +
                "          \"ProcentPromocji\": 38,\n" +
                "          \"MiastoWyjazduUrl\": \"warszawa\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"InneDlugosciPobytuWInnychTerminach\": true,\n" +
                "      \"FlagiProduktu\": {\n" +
                "        \"CzyAlertMaloMiejsc\": true,\n" +
                "        \"CzyBezPaszportu\": true,\n" +
                "        \"CzyTerminPotwierdzony\": null,\n" +
                "        \"CzyLastMinute\": false,\n" +
                "        \"CzyRekomendowana\": false,\n" +
                "        \"CzyJestFigloklub\": false,\n" +
                "        \"CzyPromocjaHappyHours\": false,\n" +
                "        \"CzyJestPolskaStrefa\": false,\n" +
                "        \"CzyWczasyZRabatem\": false,\n" +
                "        \"CzyHotOferta\": false,\n" +
                "        \"CzyDreamliner\": false,\n" +
                "        \"CzySamolotem\": true\n" +
                "      },\n" +
                "      \"Blok1\": {\n" +
                "        \"HotelId\": 1245,\n" +
                "        \"NazwaProduktu\": null,\n" +
                "        \"NazwaUrlProduktu\": \"zakynthos-wczasy\",\n" +
                "        \"NazwaHotelu\": \"Zante Village\",\n" +
                "        \"NazwaUrlHotelu\": \"zante-village\",\n" +
                "        \"GwiazdkiHotelu\": 3.5,\n" +
                "        \"Lokalizacja\": [\n" +
                "          {\n" +
                "            \"NazwaLokalizacji\": \"Grecja\",\n" +
                "            \"NazwaLokalizacjiUrl\": \"grecja\",\n" +
                "            \"CzyRegion\": false,\n" +
                "            \"PokazWFiltrzeWWW\": true\n" +
                "          },\n" +
                "          {\n" +
                "            \"NazwaLokalizacji\": \"Zakynthos\",\n" +
                "            \"NazwaLokalizacjiUrl\": \"grecja/zakynthos\",\n" +
                "            \"CzyRegion\": true,\n" +
                "            \"PokazWFiltrzeWWW\": true\n" +
                "          }\n" +
                "        ]\n" +
                "      },\n" +
                "      \"DaneZFiltrow\": {\n" +
                "        \"CzyCenaZaWszystkich\": false,\n" +
                "        \"LiczbaDoroslych\": 2,\n" +
                "        \"LiczbaDzieci\": 1,\n" +
                "        \"LiczbaPokoi\": 1,\n" +
                "        \"WiekiDzieci\": [\n" +
                "          0\n" +
                "        ]\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }

    @Autowired
    public void setGson(@Qualifier("gson") Gson gson) {
        this.gson = gson;
    }

    @Autowired
    public void setParserService(@Qualifier("parserServiceImpl") ParserService parserService) {
        this.parserService = parserService;
    }
}