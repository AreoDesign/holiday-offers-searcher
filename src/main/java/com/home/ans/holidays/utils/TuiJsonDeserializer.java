package com.home.ans.holidays.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.home.ans.holidays.model.cdto.TuiOfferClientDto;

import java.lang.reflect.Type;

public class TuiJsonDeserializer implements JsonDeserializer<TuiOfferClientDto> {

    @Override
    public TuiOfferClientDto deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context)
            throws JsonParseException {

        JsonObject json = jsonElement.getAsJsonObject();

        return TuiOfferClientDto.builder()
                .offerCode(json.get("offerCode").getAsString())
                .offerUrl(json.get("offerUrl").getAsString())
                .departureDateAndTime(json.get("departureDate").getAsString() + 'T' + json.get("departureTime").getAsString())
                .boardType(json.get("boardType").getAsString())
                .originalPerPersonPrice(json.get("originalPerPersonPrice").getAsInt())
                .discountPerPersonPrice(json.get("discountPerPersonPrice").getAsInt())
                .duration(json.get("duration").getAsInt())
                .hotelCode(json.get("hotelCode").getAsString())
                .hotelName(json.get("hotelName").getAsString())
                .hotelStandard(json.get("hotelStandard").getAsDouble())
                .destination(getDestination(json))
                .build();
    }

    private String getDestination(JsonObject json) {
        StringBuilder destination = new StringBuilder();
        json.get("breadcrumbs").getAsJsonArray().iterator().forEachRemaining(
                element -> destination.append(element.getAsJsonObject().get("label").getAsString()).append('/')
        );
        return destination.substring(0, destination.length() - 1);
    }

}
