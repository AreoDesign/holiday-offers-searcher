package com.home.ans.holidays.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.home.ans.holidays.dictionary.RainbowTranslator;
import com.home.ans.holidays.model.cdto.RainbowOfferClientDto;

import java.lang.reflect.Type;

public class RainbowJsonDeserializer implements JsonDeserializer<RainbowOfferClientDto> {

    @Override
    public RainbowOfferClientDto deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context)
            throws JsonParseException {

        JsonObject json = jsonElement.getAsJsonObject();

        return RainbowOfferClientDto.builder()
                .ofertaId(getString(json, RainbowTranslator.OFERTA_ID))
                .ofertaUrl(getString(json, RainbowTranslator.OFERTA_URL))
                .dataWKodzieProduktu(getString(json, RainbowTranslator.DATA_W_KODZIE_PRODUKTU))
                .wyzywienie(getStringFromJsonArray(json, RainbowTranslator.WYZYWIENIE))
                .ocenaOgolna(getStringFromNestedJson(json, RainbowTranslator.OCENA_OGOLNA))
                .cenaPrzedPromocja(getIntegerFromJsonArray(json, RainbowTranslator.CENA_PRZED_PROMOCJA))
                .cenaAktualna(getIntegerFromJsonArray(json, RainbowTranslator.CENA_AKTUALNA))
                .liczbaDni(getIntegerFromJsonArray(json, RainbowTranslator.LICZBA_DNI))
                .procentPromocji(getIntegerFromJsonArray(json, RainbowTranslator.PROCENT_PROMOCJI))
                .hotelId(getIntegerFromNestedJson(json, RainbowTranslator.HOTEL_ID))
                .nazwaHotelu(getStringFromNestedJson(json, RainbowTranslator.NAZWA_HOTELU))
                .gwiazdkiHotelu(getDoubleFromNestedJson(json, RainbowTranslator.GWIAZDKI_HOTELU))
                .lokalizacja(getNestedLokalizacja(json))
                .build();
    }

    private String getNestedLokalizacja(JsonObject json) {
        StringBuilder lokalizacja = new StringBuilder();
        json.get(RainbowTranslator.LOKALIZACJA.getNestName()).getAsJsonObject()
                .get(RainbowTranslator.LOKALIZACJA.getResponseKey()).getAsJsonArray().iterator().forEachRemaining(
                element -> lokalizacja.append(element.getAsJsonObject().get("NazwaLokalizacji").getAsString()).append('/')
        );
        return lokalizacja.substring(0, lokalizacja.length() - 1);
    }

    private String getString(JsonObject jsonObject, RainbowTranslator translator) {
        return jsonObject.get(translator.getResponseKey()).getAsString();
    }

    private String getStringFromNestedJson(JsonObject jsonObject, RainbowTranslator translator) {
        return jsonObject.get(translator.getNestName()).getAsJsonObject().get(translator.getResponseKey()).getAsString();
    }

    private Integer getIntegerFromNestedJson(JsonObject jsonObject, RainbowTranslator translator) {
        return jsonObject.get(translator.getNestName()).getAsJsonObject().get(translator.getResponseKey()).getAsInt();
    }

    private Double getDoubleFromNestedJson(JsonObject jsonObject, RainbowTranslator translator) {
        return jsonObject.get(translator.getNestName()).getAsJsonObject().get(translator.getResponseKey()).getAsDouble();
    }

    private Integer getIntegerFromJsonArray(JsonObject jsonObject, RainbowTranslator translator) {
        JsonArray jsonArray = jsonObject.get(translator.getNestName()).getAsJsonArray();
        return jsonArray.get(0).getAsJsonObject().get(translator.getResponseKey()).getAsInt();
    }

    private String getStringFromJsonArray(JsonObject jsonObject, RainbowTranslator translator) {
        JsonArray jsonArray = jsonObject.get(translator.getNestName()).getAsJsonArray();
        return jsonArray.get(0).getAsJsonObject().get(translator.getResponseKey()).getAsString();
    }

}
