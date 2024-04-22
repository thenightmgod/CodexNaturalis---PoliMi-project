package it.polimi.ingsw.Model.situaCorner;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.stream.Stream;


public class CardResDeserializer implements JsonDeserializer<CardRes> {
    public CardRes deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException{
        return Stream.of(CornerState.values(), it.polimi.ingsw.Model.situaCorner.Objects.values(), Resources.values())
                .flatMap(Stream :: of)
                .filter(x ->x.name().equals(json.getAsString()))
                .findAny().orElse(null);
    }

}

