package com.home.ans.holidays.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.home.ans.holidays.dictionary.Translator;
import com.home.ans.holidays.model.cdto.RainbowOfferClientDto;

import java.lang.reflect.Type;

public class RainbowJsonDeserializer implements JsonDeserializer<RainbowOfferClientDto> {

    @Override
    public RainbowOfferClientDto deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context)
            throws JsonParseException {

        JsonObject json = jsonElement.getAsJsonObject();

        return RainbowOfferClientDto.builder()
                .ofertaId(getString(json, Translator.OFERTA_ID))
                .ofertaUrl(getString(json, Translator.OFERTA_URL))
                .dataWKodzieProduktu(getString(json, Translator.DATA_W_KODZIE_PRODUKTU))
                .ocenaOgolna(getStringFromNestedJson(json, Translator.OCENA_OGOLNA))
                .cenaPrzedPromocja(getIntegerFromJsonArray(json, Translator.CENA_PRZED_PROMOCJA))
                .cenaAktualna(getIntegerFromJsonArray(json, Translator.CENA_AKTUALNA))
                .liczbaDni(getIntegerFromJsonArray(json, Translator.LICZBA_DNI))
                .procentPromocji(getIntegerFromJsonArray(json, Translator.PROCENT_PROMOCJI))
                .hotelId(getIntegerFromNestedJson(json, Translator.HOTEL_ID))
                .nazwaHotelu(getStringFromNestedJson(json, Translator.NAZWA_HOTELU))
                .gwiazdkiHotelu(getDoubleFromNestedJson(json, Translator.GWIAZDKI_HOTELU))
                .lokalizacja(getNestedLokalizacja(json))
                .build();
    }

    private String getNestedLokalizacja(JsonObject json) {
        StringBuilder lokalizacja = new StringBuilder();
        json.get(Translator.LOKALIZACJA.getNestName()).getAsJsonObject()
                .get(Translator.LOKALIZACJA.getResponseKey()).getAsJsonArray().iterator().forEachRemaining(
                element -> lokalizacja.append(element.getAsJsonObject().get("NazwaLokalizacji").getAsString()).append('/')
        );
        return lokalizacja.substring(0, lokalizacja.length() - 1);
    }

    private String getString(JsonObject jsonObject, Translator translator) {
        return jsonObject.get(translator.getResponseKey()).getAsString();
    }

    private String getStringFromNestedJson(JsonObject jsonObject, Translator translator) {
        return jsonObject.get(translator.getNestName()).getAsJsonObject().get(translator.getResponseKey()).getAsString();
    }

    private Integer getIntegerFromNestedJson(JsonObject jsonObject, Translator translator) {
        return jsonObject.get(translator.getNestName()).getAsJsonObject().get(translator.getResponseKey()).getAsInt();
    }

    private Double getDoubleFromNestedJson(JsonObject jsonObject, Translator translator) {
        return jsonObject.get(translator.getNestName()).getAsJsonObject().get(translator.getResponseKey()).getAsDouble();
    }

    private Integer getIntegerFromJsonArray(JsonObject jsonObject, Translator translator) {
        JsonArray jsonArray = jsonObject.get(translator.getNestName()).getAsJsonArray();
        return jsonArray.get(0).getAsJsonObject().get(translator.getResponseKey()).getAsInt();
    }

}
